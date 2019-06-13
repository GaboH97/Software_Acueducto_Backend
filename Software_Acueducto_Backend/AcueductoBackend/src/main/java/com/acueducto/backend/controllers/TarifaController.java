package com.acueducto.backend.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acueducto.backend.models.entity.Tarifa;
import com.acueducto.backend.services.ITarifaService;

@Controller
public class TarifaController {

	@Autowired
	// @Qualifier("suscriptorDAOJPA")
	private ITarifaService tarifaService;

	@GetMapping("/tarifas")
	public @ResponseBody List<Tarifa> findAll() {
		return tarifaService.findAll();
	}

	@GetMapping("/tarifas/{id}")
	public @ResponseBody Tarifa findById(@PathVariable int id) {
		return tarifaService.findById(id);
	}

	@DeleteMapping("/tarifas/{id}")
	public void deleteTarifa(@PathVariable int id) {
		tarifaService.delete(id);
	}

	@PostMapping("/tarifas")
	public String createTarifa(@Valid @RequestBody Tarifa tarifa) {
		if(tarifaService.findById(tarifa.getId())==null) {
			tarifaService.save(tarifa);
			return "Tarifa creada con éxito";
		}else {
			return "Tarifa ya existe";
		}
	}
			
}
