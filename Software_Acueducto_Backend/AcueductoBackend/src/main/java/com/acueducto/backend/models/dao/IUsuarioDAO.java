package com.acueducto.backend.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.acueducto.backend.models.entity.Usuario;

public interface IUsuarioDAO extends CrudRepository<Usuario, String> {

	public Usuario findByUsuario(String usuario);
}
