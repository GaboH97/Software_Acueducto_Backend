package com.acueducto.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acueducto.backend.models.entity.Lugar;
import com.acueducto.backend.models.entity.Suscriptor;
import com.acueducto.backend.services.ILugarService;

@Controller
@CrossOrigin(origins = { "http://localhost:4200" })
public class LugaresController {

	@Autowired
	private ILugarService lugarService;

	@GetMapping("/lugares")
	public @ResponseBody List<Lugar> findAll() {
		return lugarService.findAll();
	}

	@GetMapping("/lugares/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		Lugar lugar = null;
		try {
			lugar = lugarService.findById(id);
		} catch (DataAccessException e) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "Error al realizar consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (lugar == null) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "El Lugar con ID ".concat(String.valueOf(id).concat(" no se encontró")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		System.out.println("aqui si");
		return new ResponseEntity<Lugar>(lugar, HttpStatus.OK);
	}

	@DeleteMapping("/lugares/{id}")
	public ResponseEntity<?> deleteLugar(@PathVariable int id) {
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			lugarService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar lugar de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Lugar eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/lugares")
	public ResponseEntity<?> createLugarMunicipio(@Valid @RequestBody Lugar lugar) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			lugarService.save(lugar);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al hacer registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Municipio creado con éxito");
		response.put("lugar", lugar);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PostMapping("/lugares/{idMunicipio}")
	public ResponseEntity<?> createLugarVereda(@Valid @RequestBody Lugar lugar, @PathVariable int idMunicipio) {
		Map<String, Object> response = new HashMap<String, Object>();

		Lugar municipio = lugarService.findById(idMunicipio);
		lugar.setUbicado(municipio);
		
		try {
			lugarService.save(lugar);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al hacer registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Vereda creada con éxito");
		response.put("lugar", lugar);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/lugares/tipo/{tipo}")
	public @ResponseBody List<Lugar> findByTipo(@PathVariable String tipo) {
		return lugarService.findByTipo(tipo);
	}
}
