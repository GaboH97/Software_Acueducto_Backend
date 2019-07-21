package com.acueducto.backend.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.acueducto.backend.models.entity.Lugar;

public interface ILugarDAO extends CrudRepository<Lugar, Integer>{

	public List<Lugar> findByTipo(String tipo);
	
	public Lugar findByNombreIgnoreCase(String nombre);
	
	@Query("select count(p) from Predio p where p.vereda.id=?1 ")
	public int numeroPrediosAsociados(int id);
}
