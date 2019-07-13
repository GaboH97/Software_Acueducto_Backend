package com.acueducto.backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acueducto.backend.models.dao.IUsuarioDAO;
import com.acueducto.backend.models.entity.Usuario;

@Service

//USER DETAILS SERVICE SIRVE PARA EL PROCESO DE AUTENTICACIÓN
public class UsuarioService implements IUsuarioService, UserDetailsService {

	@Autowired
	private IUsuarioDAO usuarioDAO;

	@Override
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Usuario usuario) {
		usuarioDAO.save(usuario);
	}

	@Override
	@Transactional
	public Usuario findByCedula(String cedula) {
		return usuarioDAO.findById(cedula).orElse(null);
	}

	@Override
	@Transactional
	public void deleteByCedula(String cedula) {
		usuarioDAO.deleteById(cedula);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {

		Usuario user = usuarioDAO.findByUsuario(usuario);

		if (user == null) {
			throw new UsernameNotFoundException("El usuario " + usuario + " no existe en el sistema");
		}

		/**
		 * Obtiene los roles del usuario y los convierte a una lista de GrantedAuthority
		 * a través de su implementación SimpleGrantedAuthority
		 */
		List<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map(r -> new SimpleGrantedAuthority(r.getNombre()))
				.peek(auth -> System.out.println("Rol "+auth.getAuthority()))
				.collect(Collectors.toList());

		return new User(user.getUsuario(), user.getContrasena(), user.getActivo(), true, true, true,
				authorities);
	}

}
