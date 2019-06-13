package com.acueducto.backend.services;

import java.util.List;

import com.acueducto.backend.models.entity.Factura;

public interface IFacturaService {
	
	public List<Factura> findAll();

	public void save(Factura factura);

	public Factura findById(Integer id);

	public void delete(Integer id);
	
	public Factura fetchByIdWithDetalleFacturaWithTarifa(Integer id);

}
