package com.acueducto.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acueducto.backend.models.dao.ILugarDAO;
import com.acueducto.backend.models.dao.IPredioDAO;
import com.acueducto.backend.models.entity.Lugar;
import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.PredioID;

@Service
public class PredioService implements IPredioService{

	@Autowired
	private IPredioDAO predioDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<Predio> findAll() {
		return (List<Predio>) predioDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Predio predio) {
		predioDAO.save(predio);
	}

	@Override
	public Predio findByPredioID(PredioID predioID) {
		return predioDAO.findById(predioID).orElse(null);
	}

	@Override
	@Transactional
	public void delete(PredioID predioID) {
		predioDAO.deleteById(predioID);
	}

	@Override
	public Predio fetchByIdWithAsignaciones(PredioID predioID) {
		
		return predioDAO.fetchByIdWithAsignaciones(predioID.getLugarId(),predioID.getNumeroMatricula());
	}

}
