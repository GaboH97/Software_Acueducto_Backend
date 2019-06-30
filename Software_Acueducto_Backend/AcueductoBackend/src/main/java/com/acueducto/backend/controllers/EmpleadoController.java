package com.acueducto.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import org.springframework.web.bind.annotation.ResponseBody;

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
			System.out.println("perra");
			empleadoService.save(empleado);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al hacer registro en la base de datos");
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
}
