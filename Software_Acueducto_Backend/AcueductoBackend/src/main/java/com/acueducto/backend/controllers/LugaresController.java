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

import com.acueducto.backend.models.entity.Lugar;
import com.acueducto.backend.services.ILugarService;

@Controller
public class LugaresController {

	@Autowired
	// @Qualifier("suscriptorDAOJPA")
	private ILugarService lugarService;

	@GetMapping("/lugares")
	public @ResponseBody List<Lugar> findAll() {
		return lugarService.findAll();
	}

	@GetMapping("/lugares/{id}")
	public @ResponseBody Lugar findById(@PathVariable int id) {
		return lugarService.findById(id);
	}

	@DeleteMapping("/lugares/{id}")
	public void deleteSuscriptor(@PathVariable int id) {
		lugarService.delete(id);
	}

	@PostMapping("/lugares")
	public ResponseEntity<Lugar> createLugarMunicipio(@Valid @RequestBody Lugar lugar) {
		lugarService.save(lugar);
		return new ResponseEntity<Lugar>(lugar, HttpStatus.CREATED);
	}
	
	@PostMapping("/lugares/{idMunicipio}")
	public ResponseEntity<Lugar> createLugarVereda(@Valid @RequestBody Lugar lugar, @PathVariable int idMunicipio) {
		Lugar municipio = lugarService.findById(idMunicipio);
		lugar.setUbicado(municipio);
		System.out.println("Municipio es "+municipio.getNombre());
		lugarService.save(lugar);
		return new ResponseEntity<Lugar>(lugar, HttpStatus.CREATED);
	}
	
	@GetMapping("/lugares/tipo/{tipo}")
	public @ResponseBody List<Lugar> findByTipo(@PathVariable String tipo){
		return lugarService.findByTipo(tipo);
	}
}
