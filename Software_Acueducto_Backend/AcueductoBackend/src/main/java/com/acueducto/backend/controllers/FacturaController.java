package com.acueducto.backend.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.acueducto.backend.exceptions.PredioNotFoundException;
import com.acueducto.backend.models.entity.Factura;
import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.Suscriptor;
import com.acueducto.backend.services.IFacturaService;
import com.acueducto.backend.utils.Utils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class FacturaController {

	@Autowired
	private IFacturaService facturaService;

	@Secured({ "ROLE_ADMIN", "ROLE_FONTANERO", "ROLE_TESORERO" })
	@GetMapping("/facturas")
	public @ResponseBody List<Factura> findAll() {
		return facturaService.findAll();
	}

	@GetMapping("/facturas/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		Factura factura = null;
		try {
			factura = facturaService.findById(id);
		} catch (DataAccessException e) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "Error al realizar consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (factura == null) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "La factura con ID ".concat(String.valueOf(id).concat(" no se encontró")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Factura>(factura, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/facturas/{id}")
	public ResponseEntity<?> deleteFactura(@PathVariable int id) {
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			facturaService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar factura  de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Factura eliminada con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_FONTANERO", "ROLE_TESORERO" })
	@PostMapping("/facturas")
	public ResponseEntity<?> createFactura(@Valid @RequestBody Factura factura) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			facturaService.save(factura);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al hacer registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Factura creada con éxito");
		response.put("factura", factura);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

//	@GetMapping("/facturas/{id}/detalles")
//	public @ResponseBody Factura fetchFacturaByIdWithDetallesFacturaWithTarifas(@PathVariable Integer id) {
//		Factura factura = facturaService.fetchByIdWithDetalleFacturaWithTarifa(id);
//		return facturaService.fetchByIdWithDetalleFacturaWithTarifa(id);
//	}

	@Secured({ "ROLE_ADMIN", "ROLE_TESORERO" })
	@PutMapping("/facturas/{id}")
	public ResponseEntity<?> updateFactura(@Valid @RequestBody Factura factura, @PathVariable Integer id,
			BindingResult result) {

		Map<String, Object> response = new HashMap<String, Object>();
		Factura facturaAux = facturaService.findById(id);

		if (facturaAux == null) {
			response.put("mensaje", "La Factura con ID ".concat(String.valueOf(id).concat(" no se encontró")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			facturaService.save(factura);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al hacer registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Factura actualizado con éxito");
		response.put("factura", factura);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/facturas/{id}/info")
	public ResponseEntity<?> getPredioByFacturaId(@PathVariable Integer id) {
		Factura factura = null;
		try {
			factura = facturaService.findById(id);
		} catch (DataAccessException e) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "Error al realizar consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (factura == null) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "La factura con ID".concat(String.valueOf(id).concat(" no se encontró")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		Predio predio = facturaService.findPredioByFacturaId(id);
		return new ResponseEntity<Predio>(predio, HttpStatus.OK);
	}

	@GetMapping("facturas/predios/{numeroMatricula}")
	public @ResponseBody List<Factura> getFacturasByNumeroMatriculaPredio(@PathVariable String numeroMatricula) {
		return facturaService.getFacturasByNumeroMatriculaPredio(numeroMatricula);
	}

	@GetMapping("facturas/at")
	public @ResponseBody List<Factura> getFacturasByPeriodoFacturado(
			@RequestParam("periodoFacturado") Date periodoFacturado) {
		return facturaService.getFacturasByPeriodoFacturado(periodoFacturado);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_TESORERO" })
	@PostMapping("facturas/generarFacturas")
	public ResponseEntity<?> uploadArchivo(@RequestParam("archivo") MultipartFile archivoExcel) {
		Map<String, Object> response = new HashMap<String, Object>();

		if (!archivoExcel.isEmpty()) {

			String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMYYYY_HHmmss"));

			String originalFileName = archivoExcel.getOriginalFilename();
			System.out.println(originalFileName);

			Path path = null;
			if (originalFileName.endsWith(".xlsx")) {
				path = Paths.get("uploads").resolve(fileName.concat(".xlsx")).toAbsolutePath();

			} else if (originalFileName.endsWith(".xls")) {
				path = Paths.get("uploads").resolve(fileName.concat(".xlsx")).toAbsolutePath();
			}

			int numeroFacturas = 0;

			try {

				Files.copy(archivoExcel.getInputStream(), path);
				numeroFacturas = facturaService.generarFacturas(path, numeroFacturas);
				response.put("mensaje", numeroFacturas + " facturas creadas");

			} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {

				response.put("mensaje", "Error al subir el archivo para facturación");
				response.put("error", e.getMessage().concat(" : ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (PredioNotFoundException e) {

				response.put("mensaje", e.getMessage());
				response.put("numeroFacturas", numeroFacturas + " facturas creadas creadas");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

		} else {
			response.put("error", "El archivo está vacío");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/facturas/uploads/{nombreArchivo:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {

		Path path = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
		Resource resource = null;

		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {

		}
		if (!resource.exists() && resource.isReadable()) {
			throw new RuntimeException("Error, no se pudo cargar la imagen".concat(nombreFoto));
		}

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + resource.getFilename() + "\"");
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/facturas/prueba")
	@ResponseBody
	public List<Factura> obtener(
			@RequestParam("periodoFacturado") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date periodoFacturado) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// get current date time with Date()

		System.out.println(dateFormat.format(periodoFacturado));
		return facturaService.findByPeriodoFacturado(periodoFacturado);
	}

	@GetMapping(value = "/facturas/reportes", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<ByteArrayResource> generarReporteSuscriptores(
			@RequestParam("periodoFacturado") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date periodoFacturado) {
		Path path = Paths.get(Utils.INVOICES_PER_BILLED_PERIOD_REPORT_TEMPLATE).toAbsolutePath();
		try {

			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
					facturaService.obtenerDatosFacturasPorPeriodoFacturado(periodoFacturado));

			JasperReport report = JasperCompileManager.compileReport(path.toString());

			Map<String, Object> parameters = new HashMap<>();

			// parameters.put("deudaTotal", facturaService.obtenerGranTotalDeuda());
			
			parameters.put("periodoFacturadoP", periodoFacturado);

			JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);

			byte[] pdfAsByteArray = JasperExportManager.exportReportToPdf(print);

			ByteArrayResource bar = new ByteArrayResource(pdfAsByteArray);

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=reporte_suscritores_en_mora.pdf")
					.contentType(MediaType.APPLICATION_PDF) //
					.body(bar);
		} catch (JRException e) {
			e.printStackTrace();
		}
		return null;
	}

}
