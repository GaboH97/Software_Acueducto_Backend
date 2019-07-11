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

import com.acueducto.backend.models.dao.IEmpleadoDAO;
import com.acueducto.backend.models.entity.Empleado;

@Service

//USER DETAILS SERVICE SIRVE PARA EL PROCESO DE AUTENTICACIÓN
public class EmpleadoService implements IEmpleadoService, UserDetailsService {

	@Autowired
	private IEmpleadoDAO empleadoDAO;

	@Override
	public List<Empleado> findAll() {
		return (List<Empleado>) empleadoDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Empleado empleado) {
		empleadoDAO.save(empleado);
	}

	@Override
	@Transactional
	public Empleado findByCedula(String cedula) {
		return empleadoDAO.findById(cedula).orElse(null);
	}

	@Override
	@Transactional
	public void deleteByCedula(String cedula) {
		empleadoDAO.deleteById(cedula);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {

		Empleado empleado = empleadoDAO.findByUsuario(usuario);

		if (empleado == null) {
			throw new UsernameNotFoundException("El usuario " + usuario + " no existe en el sistema");
		}

		/**
		 * Obtiene los roles del usuario y los convierte a una lista de GrantedAuthority
		 * a través de su implementación SimpleGrantedAuthority
		 */
		List<GrantedAuthority> authorities = empleado.getRoles()
				.stream()
				.map(r -> new SimpleGrantedAuthority(r.getNombre()))
				.peek(auth -> System.out.println("Rol "+auth.getAuthority()))
				.collect(Collectors.toList());

		return new User(empleado.getUsuario(), empleado.getContrasena(), empleado.getActivo(), true, true, true,
				authorities);
	}

}
