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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acueducto.backend.models.entity.Suscriptor;
import com.acueducto.backend.services.*;

@Controller
@CrossOrigin(origins = {"http://localhost:4200"})
public class SuscriptorController {

	@Autowired
	private ISuscriptorService suscriptorService;

	@GetMapping("/suscriptores")
	public @ResponseBody List<Suscriptor> findAll() {
		return suscriptorService.findAll();
	}

	@GetMapping("/suscriptores/{cedula}")
	public @ResponseBody Suscriptor findByCedula(@PathVariable String cedula) {
		return suscriptorService.findByCedula(cedula);
	}
	
	@GetMapping("/suscriptores/{cedula}/detalles")
	public @ResponseBody Suscriptor fetchByCedulaWithAsignacionesWithPredios(@PathVariable String cedula) {
		Suscriptor suscriptor =suscriptorService.fetchByCedulaWithAsignacionesWithPredios(cedula);
		//System.out.println(suscriptor.getPredios().size());
		//suscriptor.getPredios().forEach(a->System.out.println("coso"));
		return suscriptorService.fetchByCedulaWithAsignacionesWithPredios(cedula);
	}

	@DeleteMapping("/suscriptores/{cedula}")
	public @ResponseBody Suscriptor deleteSuscriptor(@PathVariable String cedula) {
		Suscriptor suscriptor = suscriptorService.findByCedula(cedula);
		if(suscriptor!=null) {
			suscriptorService.delete(cedula);
			return suscriptor;
		}else {
			System.out.println("yaper");
			return null;
		}
		
	}

//	@PostMapping("/suscriptores")
//	@ResponseBody
//	public Suscriptor createSuscriptor(@Valid @RequestBody Suscriptor suscriptor, BindingResult result) {
//		
//		if(result.hasErrors()) {
//			StringBuilder builder = new StringBuilder();
//			result.getAllErrors().forEach(e-> builder.append(e.getDefaultMessage().concat(System.getProperty("line.separator"))));
//			return suscriptor;
//		}else { 
//			if (suscriptorService.findByCedula(suscriptor.getCedula()) == null) {
//				suscriptorService.save(suscriptor);
//				return suscriptor;
//			}
//		}
//		return suscriptor;
//	}
	
	@PostMapping("/suscriptores")
	public ResponseEntity<Suscriptor> createSuscriptor(@Valid @RequestBody Suscriptor suscriptor, BindingResult result) {
		
		if(result.hasErrors()) {
			StringBuilder builder = new StringBuilder();
			result.getAllErrors().forEach(e-> builder.append(e.getDefaultMessage().concat(System.getProperty("line.separator"))));
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(builder.toString());
		}else { 
			if (suscriptorService.findByCedula(suscriptor.getCedula()) == null) {
				suscriptorService.save(suscriptor);
				return ResponseEntity.ok(suscriptor);
			}
		}
		return ResponseEntity.ok(suscriptor);
	}
	
	@PutMapping("/suscriptores/{cedula}")
	@ResponseBody
	public Suscriptor updateSuscriptor(@Valid @RequestBody Suscriptor suscriptor, @PathVariable String cedula, BindingResult result) {
		
		if(result.hasErrors()) {
			StringBuilder builder = new StringBuilder();
			result.getAllErrors().forEach(e-> builder.append(e.getDefaultMessage().concat(System.getProperty("line.separator"))));
			builder.toString();
			return suscriptor;
		}else { 
			if (suscriptorService.findByCedula(cedula) != null) {
				suscriptorService.save(suscriptor);
				return suscriptor;
			} else {
				return null;
			}
		}
	}
		
}
