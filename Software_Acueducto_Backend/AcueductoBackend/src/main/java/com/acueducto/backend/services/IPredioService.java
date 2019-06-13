package com.acueducto.backend.services;

import java.util.List;

import com.acueducto.backend.models.entity.Lugar;
import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.PredioID;

public interface IPredioService {
	
	public List<Predio> findAll();

	public void save(Predio predio);

	public Predio findByPredioID(PredioID predioID);

	public void delete(PredioID predioID);
	
	public Predio fetchByIdWithAsignaciones(PredioID id);
}
