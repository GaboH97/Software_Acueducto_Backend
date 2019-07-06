package com.acueducto.backend.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acueducto.backend.models.dao.IFacturaDAO;
import com.acueducto.backend.models.dao.IPredioDAO;
import com.acueducto.backend.models.dao.ITarifaDAO;
import com.acueducto.backend.models.entity.DetalleFactura;
import com.acueducto.backend.models.entity.Factura;
import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.Tarifa;
import com.acueducto.backend.utils.Utils;

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
		return (List<Factura>) facturaDAO.findAll();
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
	public void generarFacturas(Path path) {
		Workbook workbook;
		try {

			workbook = WorkbookFactory.create(path.toFile());
			// Retrieving the number of sheets in the Workbook
			System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

			// Getting the Sheet at index zero
			Sheet sheet = workbook.getSheetAt(0);

			Tarifa tarifaValorM3 = tarifaDAO.findByDescripcion("Valor metro cúbico");

			sheet.forEach(row -> {
				
				System.out.println(row.getCell(0).getCellTypeEnum().toString());
				System.out.println(row.getCell(1).getCellTypeEnum().toString());
				
				DetalleFactura detalleFacturaValorM3 = new DetalleFactura(tarifaValorM3);

				String numeroMatricula = row.getCell(0).getStringCellValue();
				System.out.println(numeroMatricula);
				Predio predio = predioDAO.findByNumeroMatricula(numeroMatricula);
				
				
				if (predio != null) {
					
					Double cantidad = Double.valueOf(row.getCell(1).getStringCellValue());
					detalleFacturaValorM3.setCantidad(cantidad);
					
					Factura factura = new Factura();
					factura.setPredio(predio);
					factura.getDetallesFactura().add(detalleFacturaValorM3);
					
					facturaDAO.save(factura);
					
				} else {
				}
				
			});

		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
