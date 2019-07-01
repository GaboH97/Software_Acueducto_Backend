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

import com.acueducto.backend.models.entity.Factura;
import com.acueducto.backend.models.entity.Suscriptor;
import com.acueducto.backend.services.IFacturaService;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class FacturaController {

	@Autowired
	private IFacturaService facturaService;

	@GetMapping("/facturas")
	public @ResponseBody List<Factura> findAll() {
		return facturaService.findAll();
	}

	@GetMapping("/facturas/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		Factura factura = null;
		try {
			factura = facturaService.findById(id);
		} catch (DataAccessException e) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "Error al realizar consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (factura == null) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "La factura con ID ".concat(String.valueOf(id).concat(" no se encontró")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Factura>(factura, HttpStatus.OK);
	}

	@DeleteMapping("/facturas/{id}")
	public ResponseEntity<?> deleteFactura(@PathVariable int id) {
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			facturaService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar factura  de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Factura eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/facturas")
	public ResponseEntity<?> createFactura(@Valid @RequestBody Factura factura) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			facturaService.save(factura);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al hacer registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Factura creada con éxito");
		response.put("factura", factura);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/facturas/{id}/detalles")
	public @ResponseBody Factura fetchFacturaByIdWithDetallesFacturaWithTarifas(@PathVariable Integer id) {
		Factura factura = facturaService.fetchByIdWithDetalleFacturaWithTarifa(id);
		return facturaService.fetchByIdWithDetalleFacturaWithTarifa(id);
	}
}
