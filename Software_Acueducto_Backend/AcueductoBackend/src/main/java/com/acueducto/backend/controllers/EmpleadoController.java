package com.acueducto.backend.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = {"http://localhost:4200"})
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
	public @ResponseBody Empleado deleteSuscriptor(@PathVariable String cedula) {
		Empleado empleado = empleadoService.findByCedula(cedula);
		if(empleado!=null) {
			empleadoService.delete(cedula);
			return empleado;
		}else {
			System.out.println("yaper");
			return null;
		}
		
	}

	
	@PostMapping("/empleados")
	public ResponseEntity<Empleado> createEmpleado(@Valid @RequestBody Empleado empleado, BindingResult result) {
		System.out.println("aqui");
		if(result.hasErrors()) {
			StringBuilder builder = new StringBuilder();
			result.getAllErrors().forEach(e-> builder.append(e.getDefaultMessage().concat(System.getProperty("line.separator"))));
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(builder.toString());
			System.out.println("alla");

		}else { 
			if (empleadoService.findByCedula(empleado.getCedula()) == null) {
				empleadoService.save(empleado);
				return ResponseEntity.ok(empleado);
			}
		}
		return ResponseEntity.ok(empleado);
	}
	
	@PutMapping("/empleados/{cedula}")
	@ResponseBody
	public Empleado updateSuscriptor(@Valid @RequestBody Empleado empleado, @PathVariable String cedula, BindingResult result) {
		
		if(result.hasErrors()) {
			StringBuilder builder = new StringBuilder();
			result.getAllErrors().forEach(e-> builder.append(e.getDefaultMessage().concat(System.getProperty("line.separator"))));
			builder.toString();
			return empleado;
		}else { 
			if (empleadoService.findByCedula(cedula) != null) {
				empleadoService.save(empleado);
				return empleado;
			} else {
				return null;
			}
		}
	}
}
