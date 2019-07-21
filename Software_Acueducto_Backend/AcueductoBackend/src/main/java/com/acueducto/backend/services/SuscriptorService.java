package com.acueducto.backend.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acueducto.backend.models.dao.IPredioDAO;
import com.acueducto.backend.models.dao.ISuscriptorDAO;
import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.Suscriptor;

@Service
public class SuscriptorService implements ISuscriptorService {

	@Autowired
	private ISuscriptorDAO suscriptorDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Suscriptor> findAll() {
		return (List<Suscriptor>) suscriptorDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Suscriptor suscriptor) {
		suscriptorDAO.save(suscriptor);
	}

	@Override
	public Suscriptor findByCedula(String cedula) {
		return suscriptorDAO.findById(cedula).orElse(null);
	}

	@Override
	@Transactional
	public void deleteByCedula(String cedula) {
		suscriptorDAO.deleteById(cedula);
	}

	@Override
	public List<Predio> getPrediosBySuscriptor(String cedula) {
		return suscriptorDAO.getPrediosBySuscriptor(cedula);
	}

	@Override
	public List<Map<String, Object>> obtenerTodosSuscriptores() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Suscriptor suscriptor : findAll()) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("cedula", suscriptor.getCedula());
			item.put("nombre", suscriptor.getNombre());
			item.put("apellido", suscriptor.getApellido());
			item.put("estado", suscriptor.formatEstado());
			item.put("estadoCuenta", suscriptor.formatEstadoCuenta());
			result.add(item);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> obtenerSuscriptoresEnMora() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		double total = 0;
				
		for (Suscriptor suscriptor : findAll()) {
			Map<String, Object> item = new HashMap<String, Object>();

			List<Predio> prediosSuscriptor = getPrediosBySuscriptor(suscriptor.getCedula());
			
			
			if (!prediosSuscriptor.stream().allMatch(p -> p.estaAlDia())) {
				
				List<Predio> prediosConDeuda = prediosSuscriptor.stream().filter(p -> !p.estaAlDia())
						.collect(Collectors.toList());
				
				for (Predio predio : prediosConDeuda) {
					item.put("cedula", suscriptor.getCedula());
					item.put("nombre", suscriptor.getNombre());
					item.put("apellido", suscriptor.getApellido());
					item.put("predio", predio.getNombre());
					item.put("saldoPendiente", predio.obtenerDeudaTotal());
				}
				
				total += prediosConDeuda.stream().mapToDouble(p-> p.obtenerDeudaTotal()).sum();
				
				result.add(item);
			}
		}

		return result;
	}
	
	@Override
	public double obtenerGranTotalDeuda() {
		double total = 0;
		for (Suscriptor suscriptor : findAll()) {
			List<Predio> prediosConDeuda = getPrediosBySuscriptor(suscriptor.getCedula()).stream().filter(p -> !p.estaAlDia())
					.collect(Collectors.toList());
			total += prediosConDeuda.stream().mapToDouble(p-> p.obtenerDeudaTotal()).sum();

		}
		return total;
	}
	
	@Override
	public Long obtenerNumeroSuscriptoresActivos() {
		return suscriptorDAO.countByEstado("A");
	}

}
