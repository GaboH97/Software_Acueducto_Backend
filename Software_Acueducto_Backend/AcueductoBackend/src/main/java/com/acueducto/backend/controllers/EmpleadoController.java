package com.acueducto.backend.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.hibernate.result.NoMoreReturnsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.acueducto.backend.models.entity.Empleado;
import com.acueducto.backend.models.entity.Suscriptor;
import com.acueducto.backend.services.IEmpleadoService;
import com.acueducto.backend.services.ISuscriptorService;

@Controller
@CrossOrigin(origins = { "http://localhost:4200" })
public class EmpleadoController {

	@Autowired
	private IEmpleadoService empleadoService;

	@GetMapping("/empleados")
	public @ResponseBody List<Empleado> findAll() {
		return empleadoService.findAll();
	}

	@GetMapping("/empleados/{cedula}")
	public @ResponseBody Empleado findByCedula(@PathVariable String cedula) {
		return empleadoService.findByCedula(cedula);
	}

	@DeleteMapping("/empleados/{cedula}")
	public ResponseEntity<?> deleteEmpleado(@PathVariable String cedula) {
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			Empleado empleado = empleadoService.findByCedula(cedula);
			String nombreFotoAnterior = empleado.getFoto();
			if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}
			empleadoService.deleteByCedula(cedula);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar empleado de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Empleado eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	@PostMapping("/empleados")
	public ResponseEntity<?> createEmpleado(@Valid @RequestBody Empleado empleado, BindingResult result) {
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			empleadoService.save(empleado);
		} catch (DataAccessException e) {
			response.put("mensaje", "Ya existe un usuario con el nombre '"+empleado.getUsuario()+"'");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Empleado creado con éxito");
		response.put("empleado", empleado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/empleados/{cedula}")
	@ResponseBody
	public ResponseEntity<?> updateSuscriptor(@Valid @RequestBody Empleado empleado, @PathVariable String cedula,
			BindingResult result) {

		Map<String, Object> response = new HashMap<String, Object>();
		Empleado empleadoAux = empleadoService.findByCedula(cedula);

		if (empleadoAux == null) {
			response.put("mensaje", "El empleado con cédula ".concat(cedula.concat(" no se encontró")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			empleadoService.save(empleado);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al hacer registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Empleado actualizado con éxito");
		response.put("empleado", empleado);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PostMapping("empleados/cargarFoto")
	public ResponseEntity<?> uploadPhoto(@RequestParam("foto") MultipartFile foto,
			@RequestParam("cedula") String cedula) {
		Map<String, Object> response = new HashMap<String, Object>();

		Empleado empleado = empleadoService.findByCedula(cedula);

		if (!foto.isEmpty()) {
			String nombreArchivo = UUID.randomUUID().toString().concat("_")
					.concat(foto.getOriginalFilename().replaceAll(" ", "_"));
			Path path = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
			try {
				Files.copy(foto.getInputStream(), path);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen del empleado");
				response.put("error", e.getMessage().concat(" : ").concat(e.getCause().getMessage()));

				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nombreFotoAnterior = empleado.getFoto();
			if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}
			empleado.setFoto(nombreArchivo);
			empleadoService.save(empleado);
			response.put("empleado", empleado);
			response.put("mensaje", "Se ha subido correctamente la foto");
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/empleados/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {

		Path path = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
		Resource resource = null;

		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {

		}
		if (!resource.exists() && resource.isReadable()) {
			throw new RuntimeException("Error, no se pudo cargar la imagen".concat(nombreFoto));
		}

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + resource.getFilename() + "\"");
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}

}
