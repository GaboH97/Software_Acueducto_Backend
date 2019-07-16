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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

import com.acueducto.backend.models.entity.Rol;
import com.acueducto.backend.models.entity.Suscriptor;
import com.acueducto.backend.models.entity.Usuario;
import com.acueducto.backend.services.IUsuarioService;
import com.acueducto.backend.services.ISuscriptorService;

@Controller
@CrossOrigin(origins = { "http://localhost:4200" })
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/usuarios")
	public @ResponseBody List<Usuario> findAll() {
		return usuarioService.findAll();
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/usuarios/{cedula}")
	public @ResponseBody Usuario findByCedula(@PathVariable String cedula) {
		return usuarioService.findByCedula(cedula);
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/usuarios/{cedula}")
	public ResponseEntity<?> deleteEmpleado(@PathVariable String cedula) {
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			Usuario usuario = usuarioService.findByCedula(cedula);
			String nombreFotoAnterior = usuario.getFoto();
			if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}
			usuarioService.deleteByCedula(cedula);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar empleado de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Empleado eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/usuarios")
	public ResponseEntity<?> createUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) {
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			
			usuarioService.save(usuario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Ya existe un usuario con el nombre '"+usuario.getUsuario()+"'");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Empleado creado con éxito");
		response.put("empleado", usuario);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/usuarios/{cedula}")
	@ResponseBody
	public ResponseEntity<?> updateSuscriptor(@Valid @RequestBody Usuario usuario, @PathVariable String cedula,
			BindingResult result) {

		Map<String, Object> response = new HashMap<String, Object>();
		Usuario usuarioAux = usuarioService.findByCedula(cedula);

		if (usuarioAux == null) {
			response.put("mensaje", "El empleado con cédula ".concat(cedula.concat(" no se encontró")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			
			usuarioService.save(usuario);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al hacer registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Empleado actualizado con éxito");
		response.put("empleado", usuario);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("usuarios/cargarFoto")
	public ResponseEntity<?> uploadPhoto(@RequestParam("foto") MultipartFile foto,
			@RequestParam("cedula") String cedula) {
		
		Map<String, Object> response = new HashMap<String, Object>();

		Usuario usuario = usuarioService.findByCedula(cedula);

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

			String nombreFotoAnterior = usuario.getFoto();
			if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}
			usuario.setFoto(nombreArchivo);
			usuarioService.save(usuario);
			response.put("empleado", usuario);
			response.put("mensaje", "Se ha subido correctamente la foto");
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/usuarios/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
		
		System.out.println("Obteniendo foto");
		Path path = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
		Resource resource = null;

		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {

		}
		if (!resource.exists() && resource.isReadable()) {
			System.out.println("aaayy");
			throw new RuntimeException("Error, no se pudo cargar la imagen".concat(nombreFoto));
		}

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + resource.getFilename() + "\"");
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
	
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/usuarios/roles")
	public @ResponseBody List<Rol> findAllRoles() {
		return usuarioService.findAllRoles();
	}
	
}
