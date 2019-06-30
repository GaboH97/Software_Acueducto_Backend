package com.acueducto.backend.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.acueducto.backend.models.entity.Tarifa;

public interface ITarifaDAO extends CrudRepository<Tarifa, Integer>{

	public List<Tarifa> findByDescripcionIgnoreCaseContaining(String descripcion);
}
