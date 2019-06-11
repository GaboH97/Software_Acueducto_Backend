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

import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.PredioID;
import com.acueducto.backend.services.IPredioService;

@Controller
public class PredioController {

	@Autowired
	// @Qualifier("suscriptorDAOJPA")
	private IPredioService predioService;

	@GetMapping("/predios")
	public @ResponseBody List<Predio> findAll() {
		return predioService.findAll();
	}

	@GetMapping("/predios/{vereda}/{matricula}")
	public @ResponseBody Predio findById(@PathVariable int vereda, @PathVariable String matricula) {
		PredioID predioID = new PredioID(vereda, matricula);
		//Predio predio = predioService.findByPredioID(predioID);
		//predio.getAsignaciones().forEach(System.out::println);
		return predioService.findByPredioID(predioID);
	}

	@DeleteMapping("/predios/{vereda}/{matricula}")
	public void deleteSuscriptor(@PathVariable int vereda, @PathVariable String matricula) {
		PredioID predioID = new PredioID(vereda, matricula);
		predioService.delete(predioID);
	}

	@PostMapping("/predios")
	public ResponseEntity<Predio> createSuscriptor(@Valid @RequestBody Predio predio) {
		predioService.save(predio);
		return new ResponseEntity<Predio>(predio, HttpStatus.CREATED);
	}
}
