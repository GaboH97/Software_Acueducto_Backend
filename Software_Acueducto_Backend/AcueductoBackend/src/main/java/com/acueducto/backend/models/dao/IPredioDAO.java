package com.acueducto.backend.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.PredioID;

public interface IPredioDAO extends CrudRepository<Predio,PredioID>{
	
	
}
