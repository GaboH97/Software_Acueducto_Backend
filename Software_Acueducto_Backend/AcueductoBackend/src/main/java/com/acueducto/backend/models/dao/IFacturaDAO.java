package com.acueducto.backend.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.acueducto.backend.models.entity.Factura;

public interface IFacturaDAO extends CrudRepository<Factura, Integer>{

}
