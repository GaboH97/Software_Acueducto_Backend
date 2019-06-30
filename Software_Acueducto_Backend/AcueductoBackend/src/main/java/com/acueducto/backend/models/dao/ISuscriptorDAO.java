package com.acueducto.backend.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.Suscriptor;

public interface ISuscriptorDAO extends CrudRepository<Suscriptor, String>{
	
	@Query("select p from Predio p join fetch p.suscriptor s where s.cedula=?1")
	public List<Predio> getPrediosBySuscriptor(String cedula);

	
}
