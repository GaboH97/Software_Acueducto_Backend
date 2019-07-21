package com.acueducto.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acueducto.backend.models.dao.IPredioDAO;
import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.PredioID;
import com.acueducto.backend.models.entity.Tarifa;

@Service
public class PredioService implements IPredioService{

	@Autowired
	private IPredioDAO predioDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<Predio> findAll() {
		return (List<Predio>) predioDAO.buscarConSuscriptor();
	}

	@Override
	@Transactional
	public void save(Predio predio) {
		predioDAO.save(predio);
	}
	
	@Override
	public Predio findByNumeroMatricula(String numeroMatricula) {
		return predioDAO.findByNumeroMatriculaWithSuscriptor(numeroMatricula).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(String numeroMatricula) {
		predioDAO.deleteById(numeroMatricula);
	}

	@Override
	public Predio findByPredioID(PredioID predioID) {
		return null;
	}

	@Override
	public void delete(PredioID predioID) {		
	}

	@Override
	public List<Predio> findByNombre(String nombre) {
		return predioDAO.findByNombreIgnoreCaseContaining(nombre);
	}

}
