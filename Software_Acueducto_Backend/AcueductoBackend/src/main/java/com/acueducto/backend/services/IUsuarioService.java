package com.acueducto.backend.services;

import java.util.List;

import com.acueducto.backend.models.entity.Rol;
import com.acueducto.backend.models.entity.Usuario;

public interface IUsuarioService {
	
	public List<Usuario> findAll();

	public void save(Usuario usuario);

	public Usuario findByCedula(String cedula);

	public void deleteByCedula(String cedula);
	
	public Usuario findByUsuario(String usuario);

	public List<Rol> findAllRoles();

	public int obtenerNumeroUsuariosActivos();
	
	
	
	
}
