package com.acueducto.backend.services;

import java.util.List;

import com.acueducto.backend.models.entity.Suscriptor;

public interface ISuscriptorService {
	
	public List<Suscriptor> findAll();
	
	public void save(Suscriptor suscriptor);
	
	public Suscriptor findByCedula(String cedula);
	
	public void deleteByCedula(String cedula);

}
