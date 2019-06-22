package com.acueducto.backend.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.acueducto.backend.models.entity.Lugar;

public interface ILugarDAO extends CrudRepository<Lugar, Integer>{

	public List<Lugar> findByTipo(String tipo);
}
