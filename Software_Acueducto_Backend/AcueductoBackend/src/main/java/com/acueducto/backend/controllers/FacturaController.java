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

import com.acueducto.backend.models.entity.Factura;
import com.acueducto.backend.services.IFacturaService;

@Controller
public class FacturaController {
	
	@Autowired
	// @Qualifier("suscriptorDAOJPA")
	private IFacturaService facturaService;

	@GetMapping("/facturas")
	public @ResponseBody List<Factura> findAll() {
		return facturaService.findAll();
	}

	@GetMapping("/facturas/{id}")
	public @ResponseBody Factura findById(@PathVariable int id) {
		return facturaService.findById(id);
	}

	@DeleteMapping("/facturas/{id}")
	public void deleteSuscriptor(@PathVariable int id) {
		facturaService.delete(id);
	}

	@PostMapping("/facturas")
	public ResponseEntity<Factura> createFactura(@Valid @RequestBody Factura factura) {
		facturaService.save(factura);
		return new ResponseEntity<Factura>(factura, HttpStatus.CREATED);
	}
	
	@GetMapping("/facturas/{id}/detalles")
	public @ResponseBody Factura fetchFacturaByIdWithDetallesFacturaWithTarifas(@PathVariable Integer id){
		
		Factura factura =  facturaService.fetchByIdWithDetalleFacturaWithTarifa(id);
		return facturaService.fetchByIdWithDetalleFacturaWithTarifa(id);
	}
}
