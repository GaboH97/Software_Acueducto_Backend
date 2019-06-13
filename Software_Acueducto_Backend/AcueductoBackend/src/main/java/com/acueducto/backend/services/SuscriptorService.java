package com.acueducto.backend.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acueducto.backend.models.dao.ISuscriptorDAO;
import com.acueducto.backend.models.entity.Suscriptor;

@Service
public class SuscriptorService implements ISuscriptorService{
	
	@Autowired
	private ISuscriptorDAO suscriptorDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<Suscriptor> findAll() {
		return (List<Suscriptor>) suscriptorDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Suscriptor suscriptor) {
		suscriptorDAO.save(suscriptor);
	}

	@Override
	public Suscriptor findByCedula(String cedula) {
		return suscriptorDAO.findById(cedula).orElse(null);
	}

	@Override
	@Transactional
	public void delete(String cedula) {
		suscriptorDAO.deleteById(cedula);
	}

	@Override
	public Suscriptor fetchByCedulaWithAsignacionesWithPredios(String cedula) {
		return suscriptorDAO.fetchByCedulaWithAsinacionesWithPredios(cedula);
	}
}
