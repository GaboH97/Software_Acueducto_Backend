package com.acueducto.backend.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.acueducto.backend.models.entity.Empleado;

public interface IEmpleadoDAO extends CrudRepository<Empleado, String> {

	public Empleado findByUsuario(String usuario);
}
