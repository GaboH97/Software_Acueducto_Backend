package com.acueducto.backend.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acueducto.backend.exceptions.PredioNotFoundException;
import com.acueducto.backend.models.dao.IFacturaDAO;
import com.acueducto.backend.models.dao.IPredioDAO;
import com.acueducto.backend.models.dao.ITarifaDAO;
import com.acueducto.backend.models.entity.DetalleFactura;
import com.acueducto.backend.models.entity.Factura;
import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.Suscriptor;
import com.acueducto.backend.models.entity.Tarifa;

@Service
public class FacturaService implements IFacturaService {

	@Autowired
	private IFacturaDAO facturaDAO;

	@Autowired
	private ITarifaDAO tarifaDAO;

	@Autowired
	private IPredioDAO predioDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Factura> findAll() {
		return (List<Factura>) facturaDAO.findByOrderByPeriodoFacturadoDesc();
	}

	@Override
	@Transactional
	public void save(Factura factura) {
		facturaDAO.save(factura);
	}

	@Override
	public Factura findById(Integer id) {
		return facturaDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		facturaDAO.deleteById(id);
	}

	@Override
	public Factura fetchByIdWithDetalleFacturaWithTarifa(Integer id) {
		return facturaDAO.fetchByIdWithClientWithInvoiceItemWithProduct(id);
	}

	@Override
	public Predio findPredioByFacturaId(Integer id) {
		return facturaDAO.findPredioByFacturaId(id);
	}

	@Override
	public List<Factura> getFacturasByNumeroMatriculaPredio(String numeroMatricula) {
		return facturaDAO.fetchFacturasByNumeroMatriculaPredio(numeroMatricula);
	}

	@Override
	public List<Factura> getFacturasByPeriodoFacturado(Date periodoFacturado) {
		return facturaDAO.fetchByPeriodoFacturado(periodoFacturado);
	}

	@Override
	@Transactional
	public int generarFacturas(Path path, int numeroFacturasCreadas)
			throws EncryptedDocumentException, InvalidFormatException, IOException, PredioNotFoundException {


		Workbook workbook;

		workbook = WorkbookFactory.create(path.toFile());

		// Getting the Sheet at index zero
		Sheet sheet = workbook.getSheetAt(0);

		Tarifa tarifaValorM3 = tarifaDAO.findByDescripcion("Valor metro cúbico");

		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			
			System.out.println("nUMERO "+sheet.getLastRowNum());
			Row row = sheet.getRow(i);

			if (row == null || row.getCell(0) == null) {
				System.out.println("linea en blanco");
				continue;
			} else {

				System.out.println(row.getCell(0).getCellTypeEnum().toString());
				System.out.println(row.getCell(1).getCellTypeEnum().toString());

				DetalleFactura detalleFacturaValorM3 = new DetalleFactura(tarifaValorM3);

				String numeroMatricula = row.getCell(0).getStringCellValue();
				System.out.println(numeroMatricula+" NUMERO MATRICULA");
				Predio predio = predioDAO.findByNumeroMatricula(numeroMatricula);

				if (predio != null) {

					Double cantidad = Double.valueOf(row.getCell(1).getStringCellValue());
					detalleFacturaValorM3.setCantidad(cantidad);

					Factura factura = new Factura();
					factura.setPredio(predio);
					factura.getDetallesFactura().add(detalleFacturaValorM3);

					facturaDAO.save(factura);
					numeroFacturasCreadas++;

				} else {
					throw new PredioNotFoundException("No se encontró el predio " + numeroMatricula);
				}
			}
		}
		
		return numeroFacturasCreadas;
	}
	@Override
	public List<Factura> findByPeriodoFacturado(Date periodoFacturado){
		return facturaDAO.findByPeriodoFacturado(periodoFacturado);
	}
	
	@Override
	public List<Map<String, Object>> obtenerDatosFacturasPorPeriodoFacturado(Date periodoFacturado) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Factura factura : findByPeriodoFacturado(periodoFacturado)) {
			Map<String, Object> item  = new HashMap<String, Object>();
			Predio predio = factura.getPredio();
			result.add(item);
		}
		return result;
	}
	
}
