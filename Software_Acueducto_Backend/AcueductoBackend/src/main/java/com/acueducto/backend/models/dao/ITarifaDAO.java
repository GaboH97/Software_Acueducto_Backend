package com.acueducto.backend.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.acueducto.backend.models.entity.Tarifa;

public interface ITarifaDAO extends CrudRepository<Tarifa, Integer>{

	public List<Tarifa> findByDescripcionIgnoreCaseContaining(String descripcion);
	
	public Tarifa findByDescripcion(String descripcion);

	public Tarifa findByDescripcionIgnoreCase(String descripcion);
	
	@Query("select count(d) from DetalleFactura d where d.tarifa.id=?1 ")
	public int numeroFacturasPresente(int id);
}
