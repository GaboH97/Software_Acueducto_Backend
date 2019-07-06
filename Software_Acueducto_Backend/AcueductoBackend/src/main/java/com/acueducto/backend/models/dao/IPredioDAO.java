package com.acueducto.backend.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.acueducto.backend.models.entity.Predio;

public interface IPredioDAO extends CrudRepository<Predio,String>{
	
	
	public Predio findByNumeroMatricula(String numeroMatricula);
	
	public List<Predio> findByNombreIgnoreCaseContaining(String nombre);
	
	@Query("select p from Predio p join fetch p.suscriptor s")
	public List<Predio> buscarConSuscriptor();
	
	@Query("select p from Predio p join fetch p.suscriptor s where p.numeroMatricula = ?1 ")
	public Optional<Predio> findByNumeroMatriculaWithSuscriptor(String numeroMatricula);

}
