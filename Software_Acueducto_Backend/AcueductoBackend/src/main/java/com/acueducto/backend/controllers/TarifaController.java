package com.acueducto.backend.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import com.acueducto.backend.models.entity.Tarifa;
import com.acueducto.backend.services.ITarifaService;

@Controller
@CrossOrigin(origins = { "http://localhost:4200" })
public class TarifaController {

	@Autowired
	private ITarifaService tarifaService;

	@Secured({ "ROLE_ADMIN", "ROLE_FONTANERO", "ROLE_TESORERO" })
	@GetMapping("/tarifas")
	public @ResponseBody List<Tarifa> findAll() {
		return tarifaService.findAll();
	}

	@Secured({ "ROLE_ADMIN", "ROLE_FONTANERO", "ROLE_TESORERO" })
	@GetMapping("/tarifas/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		Tarifa tarifa = null;
		try {
			tarifa = tarifaService.findById(id);
		} catch (DataAccessException e) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "Error al realizar consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (tarifa == null) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "La tarifa con ID".concat(String.valueOf(id).concat(" no se encontró")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Tarifa>(tarifa, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/tarifas/{id}")
	public ResponseEntity<?> deleteTarifa(@PathVariable int id) {
		Map<String, Object> response = new HashMap<String, Object>();

		Tarifa tarifa = tarifaService.findById(id);


		if (tarifaService.numeroFacturasPresente(id) != 0) {
			response.put("mensaje", "Esta tarifa está asociada a una o más facturas");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			try {
				tarifaService.delete(id);
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al eliminar tarifa de la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}

		response.put("mensaje", "Tarifa eliminada con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/tarifas")
	public ResponseEntity<?> createTarifa(@Valid @RequestBody Tarifa tarifa) {
		Map<String, Object> response = new HashMap<String, Object>();

		Tarifa tarifaux = tarifaService.findByDescripcionIgnoreCase(tarifa.getDescripcion());

		if (tarifaux != null) {
			response.put("mensaje", "Ya existe la tarifa '" + tarifa.getDescripcion() + "'");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {

			try {
				tarifaService.save(tarifa);
			} catch (DataAccessException e) {

				response.put("mensaje", "Error al hacer registro en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "Tarifa creada con éxito");
			response.put("tarifa", tarifa);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}
	}

	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/tarifas/{id}")
	public ResponseEntity<?> updateTarifa(@Valid @RequestBody Tarifa tarifa, @PathVariable int id) {
		Map<String, Object> response = new HashMap<String, Object>();
		Tarifa tarifaAux = tarifaService.findById(id);

		if (tarifaAux == null) {
			response.put("mensaje", "La tarifa con ID ".concat(String.valueOf(id).concat(" no se encontró")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			HistorialTarifa anteriorHistorialTarifa = tarifa.getHistorialTarifa().isEmpty() ? new HistorialTarifa()
					: tarifa.getHistorialTarifa().get(tarifa.getHistorialTarifa().size() - 1);

			anteriorHistorialTarifa.setFechaFinal(
					Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

			HistorialTarifa nuevaHistorialTarifa = new HistorialTarifa();
			nuevaHistorialTarifa.setValorTarifa(tarifa.getValorTarifa());

			tarifa.getHistorialTarifa().add(nuevaHistorialTarifa);

			// Si hay historiales de tarifa, obtiene el último, si no, crea un nuevo
			// registro

			tarifaService.save(tarifa);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al hacer registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Tarifa actualizada con éxito");
		response.put("tarifa", tarifa);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_FONTANERO", "ROLE_TESORERO" })
	@GetMapping("/tarifas/search/{descripcion}")
	public @ResponseBody List<Tarifa> obtenerTarifasPorDescripcion(@PathVariable String descripcion) {
		return tarifaService.findByDescripcion(descripcion);
	}

}
