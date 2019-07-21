package com.acueducto.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acueducto.backend.models.dao.ITarifaDAO;
import com.acueducto.backend.models.entity.HistorialTarifa;
import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.Tarifa;

@Service
public class TarifaService implements ITarifaService {

	@Autowired
	private ITarifaDAO tarifaDAO;
	
	@Override
	public List<Tarifa> findAll() {
		return (List<Tarifa>) tarifaDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Tarifa tarifa) {
		tarifaDAO.save(tarifa);
	}

	@Override
	@Transactional
	public Tarifa findById(int id) {
		return tarifaDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(int id) {
		tarifaDAO.deleteById(id);
	}
	
	@Override
	public List<Tarifa> findByDescripcion(String descripcion) {
		return tarifaDAO.findByDescripcionIgnoreCaseContaining(descripcion);
	}

	@Override
	public Tarifa findByDescripcionIgnoreCase(String descripcion) {
		return tarifaDAO.findByDescripcionIgnoreCase(descripcion);
	}
	
	@Override
	public int numeroFacturasPresente(int id) {
		return tarifaDAO.numeroFacturasPresente(id);
	}
	
}
