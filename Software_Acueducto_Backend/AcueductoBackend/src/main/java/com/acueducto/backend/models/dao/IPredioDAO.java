package com.acueducto.backend.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.PredioID;

public interface IPredioDAO extends CrudRepository<Predio,PredioID>{
	
	@Query("select p from Predio p left join fetch p.asignaciones a where p.lugar.id=?1 and p.numeroMatricula=?2")
	public Predio fetchByIdWithAsignaciones(int lugarId, String numeroMatricula);
}
