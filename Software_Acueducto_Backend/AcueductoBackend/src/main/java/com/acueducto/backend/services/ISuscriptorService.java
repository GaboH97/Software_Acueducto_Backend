package com.acueducto.backend.services;

import java.util.List;
import java.util.Map;

import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.Suscriptor;

public interface ISuscriptorService {
	
	public List<Suscriptor> findAll();
	
	public void save(Suscriptor suscriptor);
	
	public Suscriptor findByCedula(String cedula);
	
	public void deleteByCedula(String cedula);

	public List<Predio> getPrediosBySuscriptor(String cedula);

	List<Map<String, Object>> report();

}
