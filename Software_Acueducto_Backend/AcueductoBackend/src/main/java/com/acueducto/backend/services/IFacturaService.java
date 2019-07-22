package com.acueducto.backend.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.acueducto.backend.exceptions.PredioNotFoundException;
import com.acueducto.backend.models.entity.Factura;
import com.acueducto.backend.models.entity.Predio;

public interface IFacturaService {
	
	public List<Factura> findAll();

	public void save(Factura factura);

	public Factura findById(Integer id);

	public void delete(Integer id);
	
	public Factura fetchByIdWithDetalleFacturaWithTarifa(Integer id);

	public Predio findPredioByFacturaId(Integer id);

	public List<Factura> getFacturasByNumeroMatriculaPredio(String numeroMatricula);

	public List<Factura> getFacturasByPeriodoFacturado(Date periodoFacturado);

	public int generarFacturas(Path path, int numeroFacturasCreadas) throws EncryptedDocumentException, InvalidFormatException, IOException, PredioNotFoundException;

	public Factura findFirstByPredioNumeroMatriculaOrderByPeriodoFacturado(String numeroMatricula);
	
	List<Map<String, Object>> obtenerDatosFacturasPorPeriodoFacturado(Date periodoFacturado);

	List<Factura> findByPeriodoFacturado(Date periodoFacturado);

	public Double obtenerValorCarteraPendiente();

	public Double obtenerTotalRecaudado();

}
