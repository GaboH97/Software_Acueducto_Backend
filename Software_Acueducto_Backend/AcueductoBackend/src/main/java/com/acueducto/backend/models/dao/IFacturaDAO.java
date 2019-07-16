package com.acueducto.backend.models.dao;

import java.util.Date;
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

	@Query("select f from Factura f where f.predio.numeroMatricula=?1")
	public List<Factura> fetchFacturasByNumeroMatriculaPredio(String numeroMatricula);

	@Query("select f from Factura f where f.periodoFacturado=?1")
	public List<Factura> fetchByPeriodoFacturado(Date periodoFacturado);
	
	public List<Factura> findByOrderByPeriodoFacturadoDesc();
	
	@Query("select f from Factura f where year(f.periodoFacturado)=?1 and month(f.periodoFacturado)=?2")
	public List<Factura> findByPeriodoFacturado(int anio, int mes);
	
}
