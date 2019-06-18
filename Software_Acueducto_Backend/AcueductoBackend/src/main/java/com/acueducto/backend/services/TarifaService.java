package com.acueducto.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acueducto.backend.models.dao.ITarifaDAO;
import com.acueducto.backend.models.entity.HistorialTarifa;
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

}
