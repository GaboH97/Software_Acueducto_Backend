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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acueducto.backend.models.entity.HistorialPredio;
import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.Suscriptor;
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
	public ResponseEntity<?> findById(@PathVariable String matricula) {
		Predio predio = null;
		try {
			predio = predioService.findByNumeroMatricula(matricula);
		} catch (DataAccessException e) {
			System.out.println("aqui enton");
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "Error al realizar consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (predio == null) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "El Predio con número de matrícula ".concat(matricula.concat(" no se encontró")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		System.out.println("aqui si");
		return new ResponseEntity<Predio>(predio, HttpStatus.OK);
	}

	@GetMapping("/predios/search/{nombre}")
	public @ResponseBody List<Predio> findByNombre(@PathVariable String nombre) {
		return predioService.findByNombre(nombre);
	}

	@DeleteMapping("/predios/{matricula}")
	public ResponseEntity<?> deletePredio(@PathVariable String matricula) {
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			predioService.delete(matricula);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar predio de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Predio eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/predios")
	public ResponseEntity<?> createPredio(@Valid @RequestBody Predio predio) {

		Map<String, Object> response = new HashMap<String, Object>();

		Predio predioAux = predioService.findByNumeroMatricula(predio.getNumeroMatricula());

		if (predioAux != null) {
			response.put("mensaje", "Ya existe un predio con número de matrícula " + predio.getNumeroMatricula());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			HistorialPredio historialPredio = new HistorialPredio();
			predio.getHistorialPredio().add(historialPredio);

			try {
				predioService.save(predio);
			} catch (DataAccessException e) {

				response.put("mensaje", "Error al hacer registro en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "Predio creado con éxito");
			response.put("predio", predio);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}

	}

	@PutMapping("/predios/{matricula}")
	public ResponseEntity<?> updatePredio(@Valid @RequestBody Predio predio, @PathVariable String matricula) {
		Map<String, Object> response = new HashMap<String, Object>();
		Predio predioaux = predioService.findByNumeroMatricula(matricula);

		if (predioaux == null) {
			response.put("mensaje", "El Predio con matrícula ".concat(matricula.concat(" no se encontró")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			predioService.save(predio);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al hacer registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Predio actualizado con éxito");
		response.put("predio", predio);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}
