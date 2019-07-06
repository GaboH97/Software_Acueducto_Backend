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

import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.Suscriptor;
import com.acueducto.backend.services.*;

@Controller
@CrossOrigin(origins = { "http://localhost:4200" })
public class SuscriptorController {

	@Autowired
	private ISuscriptorService suscriptorService;

	@GetMapping("/suscriptores")
	public @ResponseBody List<Suscriptor> findAll() {
		return suscriptorService.findAll();
	}

	@GetMapping("/suscriptores/{cedula}")
	public ResponseEntity<?> findByCedula(@PathVariable String cedula) {
		Suscriptor suscriptor = null;
		try {
			suscriptor = suscriptorService.findByCedula(cedula);
		} catch (DataAccessException e) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "Error al realizar consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (suscriptor == null) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "El cliente con cédula ".concat(cedula.concat(" no se encontró")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		System.out.println("aqui si");
		return new ResponseEntity<Suscriptor>(suscriptor, HttpStatus.OK);
	}

	@PostMapping("/suscriptores")
	public ResponseEntity<?> createSuscriptor(@Valid @RequestBody Suscriptor suscriptor) {

		Map<String, Object> response = new HashMap<String, Object>();

		Suscriptor suscriptorAux = suscriptorService.findByCedula(suscriptor.getCedula());

		if (suscriptorAux != null) {
			response.put("mensaje", "Ya existe un suscritor con dicha cédula");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			try {
				suscriptorService.save(suscriptor);
			} catch (DataAccessException e) {

				response.put("mensaje", "Error al hacer registro en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "Cliente creado con éxito");
			response.put("suscriptor", suscriptor);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}
		
	}

	@PutMapping("/suscriptores/{cedula}")
	public ResponseEntity<?> updateSuscriptor(@Valid @RequestBody Suscriptor suscriptor, @PathVariable String cedula,
			BindingResult result) {

		Map<String, Object> response = new HashMap<String, Object>();
		Suscriptor suscriptoraux = suscriptorService.findByCedula(cedula);

		if (suscriptoraux == null) {
			response.put("mensaje", "El cliente con cédula ".concat(cedula.concat(" no se encontró")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			suscriptorService.save(suscriptor);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al hacer registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Suscriptor actualizado con éxito");
		response.put("suscriptor", suscriptor);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/suscriptores/{cedula}")
	public ResponseEntity<?> deleteSuscriptor(@PathVariable String cedula) {

		Map<String, Object> response = new HashMap<String, Object>();

		try {
			suscriptorService.deleteByCedula(cedula);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar suscriptor de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Suscriptor eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/suscriptores/{cedula}/predios")
	public @ResponseBody List<Predio> getPrediosBySuscriptor(@PathVariable String cedula) {
		System.out.println("ESTA ES LA CÉDULA "+cedula);
		return suscriptorService.getPrediosBySuscriptor(cedula);
	}
}
