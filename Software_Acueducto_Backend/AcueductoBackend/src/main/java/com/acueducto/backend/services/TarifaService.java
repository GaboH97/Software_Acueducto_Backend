package com.acueducto.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.acueducto.backend.models.dao.ITarifaDAO;
import com.acueducto.backend.models.entity.Tarifa;

public class TarifaService implements ITarifaService {

	
	@Autowired
	private ITarifaDAO tarifaDAO;
	
	@Override
	public List<Tarifa> findAll() {
		return (List<Tarifa>) tarifaDAO.findAll();
	}

	@Override
	public void save(Tarifa tarifa) {
		tarifaDAO.save(tarifa);
	}

	@Override
	public Tarifa findById(int id) {
		return tarifaDAO.findById(id).orElse(null);
	}

	@Override
	public void delete(int id) {
		tarifaDAO.deleteById(id);
	}

}
