package com.acueducto.backend.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acueducto.backend.models.dao.IRolDAO;
import com.acueducto.backend.models.dao.IUsuarioDAO;
import com.acueducto.backend.models.entity.Rol;
import com.acueducto.backend.models.entity.Usuario;

@Service

//USER DETAILS SERVICE SIRVE PARA EL PROCESO DE AUTENTICACIÓN
public class UsuarioService implements IUsuarioService, UserDetailsService {

	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	@Autowired
	private IRolDAO rolDAO;
	
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

		Usuario user = findByUsuario(usuario);

		if (user == null) {
			throw new UsernameNotFoundException("El usuario " + usuario + " no existe en el sistema");
		}

		/**
		 * Obtiene los roles del usuario y los convierte a una lista de GrantedAuthority
		 * a través de su implementación SimpleGrantedAuthority
		 */
		List<GrantedAuthority> authorities = Arrays.asList(user.getRol())
				.stream()
				.map(r -> new SimpleGrantedAuthority(r.getNombre()))
				.peek(auth -> System.out.println("Rol "+auth.getAuthority()))
				.collect(Collectors.toList());

		return new User(user.getUsuario(), user.getContrasena(), user.getActivo(), true, true, true,
				authorities);
	}
	
	@Transactional(readOnly = true)
	public Usuario findByUsuario(String usuario) {
		return usuarioDAO.findByUsuario(usuario);
	}
	
	@Override
	public List<Rol> findAllRoles() {
		return (List<Rol>) rolDAO.findAll();
	}
	
	

}
