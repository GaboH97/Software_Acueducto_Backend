package com.acueducto.backend.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("/suscriptores")
public class SuscriptorController {
	
	@Autowired
	// @Qualifier("suscriptorDAOJPA")
	private ISuscriptorService suscriptorService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody List<Suscriptor> findAll() {
		return suscriptorService.findAll();
	}
	
	@GetMapping(value="/{id}")
	public @ResponseBody Suscriptor findByCedula(@PathVariable String id){
		return suscriptorService.findByCedula(id);
	}
	
	@DeleteMapping(value="/{id}")
	public void deleteSuscriptor(@PathVariable String id) {
		suscriptorService.delete(id);
	}
	
	@PostMapping(value="/")
	public void createSuscriptor(@Valid @RequestBody Suscriptor suscriptor) {
		suscriptorService.save(suscriptor);
	}
}
