package com.acueducto.backend.services;

import java.util.List;

import com.acueducto.backend.models.entity.Factura;

public interface IFacturaService {
	
	public List<Factura> findAll();

	public void save(Factura factura);

	public Factura findById(int id);

	public void delete(int id);
}
