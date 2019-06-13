package com.acueducto.backend.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.acueducto.backend.models.entity.Suscriptor;

public interface ISuscriptorDAO extends CrudRepository<Suscriptor, String>{

	@Query("select s from Suscriptor s join fetch s.asignaciones where s.cedula = :cedula")
	public Suscriptor fetchByCedulaWithAsinacionesWithPredios(String cedula);
	
}
