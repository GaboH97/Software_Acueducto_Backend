package com.acueducto.backend.services;

import java.util.List;

import com.acueducto.backend.models.entity.Tarifa;

public interface ITarifaService {
	
	public List<Tarifa> findAll();
	
	public void save(Tarifa tarifa);
	
	public Tarifa findById(int id);
	
	public void delete(int id);
	
}
