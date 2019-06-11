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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acueducto.backend.models.entity.Suscriptor;
import com.acueducto.backend.services.*;

@Controller
public class SuscriptorController {

	@Autowired
	// @Qualifier("suscriptorDAOJPA")
	private ISuscriptorService suscriptorService;

	@GetMapping("/suscriptores")
	public @ResponseBody List<Suscriptor> findAll() {
		return suscriptorService.findAll();
	}

	@GetMapping("/suscriptores/{id}")
	public @ResponseBody Suscriptor findByCedula(@PathVariable String id) {
		return suscriptorService.findByCedula(id);
	}

	@DeleteMapping("/suscriptores/{id}")
	public void deleteSuscriptor(@PathVariable String id) {
		suscriptorService.delete(id);
	}

	@PostMapping("/suscriptores")
	@ResponseBody
	public String createSuscriptor(@Valid @RequestBody Suscriptor suscriptor, BindingResult result) {
		
		if(result.hasErrors()) {
			StringBuilder builder = new StringBuilder();
			result.getAllErrors().forEach(e-> builder.append(e.getDefaultMessage().concat(System.getProperty("line.separator"))));
			return builder.toString();
		}else { 
			if (suscriptorService.findByCedula(suscriptor.getCedula()) == null) {
				suscriptorService.save(suscriptor);
				return "Suscriptor Agregado";
			} else {
				return "Ya existe un cliente con cédula "+suscriptor.getCedula();
			}
		}
	}
		
}
