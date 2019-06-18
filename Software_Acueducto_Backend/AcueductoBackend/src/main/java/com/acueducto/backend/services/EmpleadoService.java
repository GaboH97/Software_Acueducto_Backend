package com.acueducto.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acueducto.backend.models.dao.IEmpleadoDAO;
import com.acueducto.backend.models.entity.Empleado;

@Service
public class EmpleadoService implements IEmpleadoService {

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
	public void delete(String cedula) {
		empleadoDAO.deleteById(cedula);
	}

}
