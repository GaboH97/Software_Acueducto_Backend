package com.acueducto.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acueducto.backend.models.dao.ILugarDAO;
import com.acueducto.backend.models.entity.Lugar;

@Service
public class LugarService implements ILugarService {

	@Autowired
	private ILugarDAO lugarDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<Lugar> findAll() {
		return (List<Lugar>) lugarDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Lugar lugar) {
		lugarDAO.save(lugar);
	}

	@Override
	public Lugar findById(int id) {
		return lugarDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(int id) {
		lugarDAO.deleteById(id);
	}

	@Override
	public List<Lugar> findByTipo(String tipo) {
		return lugarDAO.findByTipo(tipo);
	}

}
