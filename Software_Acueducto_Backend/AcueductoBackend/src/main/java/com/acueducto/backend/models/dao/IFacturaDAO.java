package com.acueducto.backend.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.acueducto.backend.models.entity.Factura;
import com.acueducto.backend.models.entity.Predio;

public interface IFacturaDAO extends CrudRepository<Factura, Integer>{
	
	@Query("select f from Factura f join fetch f.detallesFactura d join fetch d.tarifa where f.id=?1")
	public Factura fetchByIdWithClientWithInvoiceItemWithProduct(Integer id);
	
	@Query("select p from Predio p join p.facturas f where f.id=?1")
	public Predio findPredioByFacturaId(int id);

	@Query("select f from Factura f join f.predio p where p.numeroMatricula=?1")
	public List<Factura> fetchFacturasByNumeroMatriculaPredio(String numeroMatricula);
	
}
