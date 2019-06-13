package com.acueducto.backend.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.acueducto.backend.models.entity.Factura;

public interface IFacturaDAO extends CrudRepository<Factura, Integer>{
	
	@Query("select f from Factura f join fetch f.detallesFactura d join fetch d.tarifa where f.id=?1")
	public Factura fetchByIdWithClientWithInvoiceItemWithProduct(Integer id);
}
