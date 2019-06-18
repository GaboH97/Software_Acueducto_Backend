package com.acueducto.backend.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

import com.acueducto.backend.models.entity.HistorialTarifa;
import com.acueducto.backend.models.entity.Suscriptor;
import com.acueducto.backend.models.entity.Tarifa;
import com.acueducto.backend.services.ITarifaService;

@Controller
@CrossOrigin(origins = {"http://localhost:4200"})
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
		
		tarifa.getHistorialTarifa().forEach(e-> System.out.println(e.toString()));
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
			historialTarifa.setValorTarifa(tarifa.getValorTarifa());
		
			tarifa.getHistorialTarifa().add(historialTarifa);
			tarifaService.save(tarifa);
			return tarifa;
		}else {
			return null;
		}
	}
	
	@PutMapping("/tarifas/{id}")
	public @ResponseBody Tarifa updateTarifa(@Valid @RequestBody Tarifa tarifa, @PathVariable int id){
		if(tarifaService.findById(id)!=null) {
			
			HistorialTarifa anteriorHistorialTarifa = tarifa.getHistorialTarifa().get(0);
			anteriorHistorialTarifa.setFechaFinal(Date.from(LocalDate.now().atStartOfDay()
				      .atZone(ZoneId.systemDefault())
				      .toInstant()));
			
			HistorialTarifa nuevaHistorialTarifa = new HistorialTarifa();
			nuevaHistorialTarifa.setValorTarifa(tarifa.getValorTarifa());
		
			tarifa.getHistorialTarifa().add(nuevaHistorialTarifa);
			
			tarifaService.save(tarifa);
			return tarifa;
		}
		System.out.println("yaper");
		return null;
	}
			
}
