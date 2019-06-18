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

import com.acueducto.backend.models.entity.HistorialTarifa;
import com.acueducto.backend.models.entity.Suscriptor;
import com.acueducto.backend.models.entity.Tarifa;
import com.acueducto.backend.services.ITarifaService;

@Controller
public class TarifaController {

	@Autowired
	private ITarifaService tarifaService;

	@GetMapping("/tarifas")
	public @ResponseBody List<Tarifa> findAll() {
		return tarifaService.findAll();
	}

	@GetMapping("/tarifas/{id}")
	public ResponseEntity<Tarifa> findById(@PathVariable int id) {
		Tarifa tarifa = tarifaService.findById(id);
		if(tarifa!=null) {
			return ResponseEntity.ok().body(tarifa);
		}else {
			return null;
		}
	}

	@DeleteMapping("/tarifas/{id}")
	public @ResponseBody Tarifa deleteTarifa(@PathVariable int id) {
		Tarifa tarifa = tarifaService.findById(id);
		if(tarifa!=null) {
			tarifaService.delete(id);
			return tarifa;
		}else {
			System.out.println("yaper");
			return null;
		}
	}

	@PostMapping("/tarifas")
	public @ResponseBody Tarifa createTarifa(@Valid @RequestBody Tarifa tarifa) {
		if(tarifaService.findById(tarifa.getId())==null) {
			HistorialTarifa historialTarifa = new HistorialTarifa();
			tarifa.getHistorialTarifa().add(historialTarifa);
			tarifaService.save(tarifa);
			return tarifa;
		}else {
			return tarifa;
		}
	}
			
}
