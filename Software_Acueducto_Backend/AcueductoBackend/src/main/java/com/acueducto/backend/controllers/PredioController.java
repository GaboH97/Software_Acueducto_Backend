package com.acueducto.backend.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acueducto.backend.models.entity.HistorialPredio;
import com.acueducto.backend.models.entity.HistorialTarifa;
import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.PredioID;
import com.acueducto.backend.services.IPredioService;

@Controller
@CrossOrigin(origins = { "http://localhost:4200" })
public class PredioController {

	@Autowired
	private IPredioService predioService;

	@GetMapping("/predios")
	public @ResponseBody List<Predio> findAll() {
		return predioService.findAll();
	}
	
	@GetMapping("/predios/{matricula}")
	public @ResponseBody Predio findById(@PathVariable String matricula) {
		return predioService.findByNumeroMatriculaWithSuscriptor(matricula);
	}
	
	@GetMapping("/predios/search/{nombre}")
	public @ResponseBody List<Predio> findByNombre(@PathVariable String nombre) {
		System.out.println("Aqui");
		return predioService.findByNombre(nombre);
	}
	


//	@GetMapping("/predios/{vereda}/{matricula}")
//	public @ResponseBody Predio findById(@PathVariable int vereda, @PathVariable String matricula) {
//		System.out.println("vereda: " + vereda + " mat " + matricula);
//		PredioID predioID = new PredioID(vereda, matricula);
//		return predioService.findByPredioID(predioID);
//	}
	
	

	@DeleteMapping("/predios/{vereda}/{matricula}")
	public @ResponseBody Predio deletePredio(@PathVariable int vereda, @PathVariable String matricula) {
		PredioID predioID = new PredioID(vereda, matricula);
		Predio predio = predioService.findByPredioID(predioID);
		predioService.delete(predioID);
		return predio;
	}

	@PostMapping("/predios")
	public ResponseEntity<Predio> createPredio(@Valid @RequestBody Predio predio) {
		
		HistorialPredio historialPredio = new HistorialPredio();
		predio.getHistorialPredio().add(historialPredio);
		predioService.save(predio);
		return new ResponseEntity<Predio>(predio, HttpStatus.CREATED);
	}
	
	@PutMapping("/predios/{numeroMatricula}")
	public @ResponseBody Predio updatePredio(@Valid @RequestBody Predio predio, @PathVariable String numeroMatricula){
		if(predioService.findByNumeroMatriculaWithSuscriptor(numeroMatricula)!=null) {
			predioService.save(predio);
			return predio;
		}else {
			return null;
		}
	}
}
