package com.acueducto.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acueducto.backend.models.dao.IFacturaDAO;
import com.acueducto.backend.models.entity.Factura;
import com.acueducto.backend.models.entity.Predio;

@Service
public class FacturaService implements IFacturaService {

	@Autowired
	private IFacturaDAO facturaDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Factura> findAll() {
		return (List<Factura>) facturaDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Factura factura) {
		facturaDAO.save(factura);
	}

	@Override
	public Factura findById(Integer id) {
		return facturaDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		facturaDAO.deleteById(id);
	}

	@Override
	public Factura fetchByIdWithDetalleFacturaWithTarifa(Integer id) {
		return facturaDAO.fetchByIdWithClientWithInvoiceItemWithProduct(id);
	}
	
	@Override
	public Predio findPredioByFacturaId(Integer id) {
		return facturaDAO.findPredioByFacturaId(id);
	}

	@Override
	public List<Factura> getFacturasByNumeroMatriculaPredio(String numeroMatricula) {
		return facturaDAO.fetchFacturasByNumeroMatriculaPredio(numeroMatricula);
	}
}
