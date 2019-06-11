package com.acueducto.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acueducto.backend.models.dao.IFacturaDAO;
import com.acueducto.backend.models.entity.Factura;

@Service
public class FacturaService implements IFacturaService {

	@Autowired
	private IFacturaDAO facturaDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<Factura> findAll() {
		return (List<Factura>) facturaDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Factura factura) {
		facturaDAO.save(factura);
	}

	@Override
	public Factura findById(int id) {
		return facturaDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(int id) {
		facturaDAO.deleteById(id);
	}
}
