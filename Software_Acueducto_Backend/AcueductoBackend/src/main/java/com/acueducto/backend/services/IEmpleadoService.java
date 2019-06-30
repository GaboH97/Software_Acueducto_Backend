package com.acueducto.backend.services;

import java.util.List;

import com.acueducto.backend.models.entity.Empleado;
import com.acueducto.backend.models.entity.Factura;

public interface IEmpleadoService {
	
	public List<Empleado> findAll();

	public void save(Empleado empleado);

	public Empleado findByCedula(String cedula);

	public void deleteByCedula(String cedula);
	
}
