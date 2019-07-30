package com.acueducto.backend.controllers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.DataAccessException;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.acueducto.backend.models.entity.Predio;
import com.acueducto.backend.models.entity.Suscriptor;
import com.acueducto.backend.services.*;
import com.acueducto.backend.utils.Utils;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@CrossOrigin(origins = { "http://localhost:4200" })
public class SuscriptorController {

	@Autowired
	private ISuscriptorService suscriptorService;
	
	@GetMapping("/suscriptores")
	public @ResponseBody List<Suscriptor> findAll() {
		return suscriptorService.findAll();
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/suscriptores/search/{nombreOrApellido}")
	public @ResponseBody List<Suscriptor> findByNombreOrApellido(@PathVariable String nombreOrApellido){
		System.out.println("ESTE ES "+nombreOrApellido);
		return suscriptorService.findByNombreOrApellido(nombreOrApellido);
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/suscriptores/{cedula}")
	public ResponseEntity<?> findByCedula(@PathVariable String cedula) {
		Suscriptor suscriptor = null;
		try {
			suscriptor = suscriptorService.findByCedula(cedula);
		} catch (DataAccessException e) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "Error al realizar consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (suscriptor == null) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("mensaje", "El cliente con cédula ".concat(cedula.concat(" no se encontró")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Suscriptor>(suscriptor, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/suscriptores")
	public ResponseEntity<?> createSuscriptor(@Valid @RequestBody Suscriptor suscriptor) {

		Map<String, Object> response = new HashMap<String, Object>();

		Suscriptor suscriptorAux = suscriptorService.findByCedula(suscriptor.getCedula());

		if (suscriptorAux != null) {
			response.put("mensaje", "Ya existe un suscritor con dicha cédula");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			try {
				suscriptorService.save(suscriptor);
			} catch (DataAccessException e) {

				response.put("mensaje", "Error al hacer registro en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "Cliente creado con éxito");
			response.put("suscriptor", suscriptor);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}

	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/suscriptores/{cedula}")
	public ResponseEntity<?> updateSuscriptor(@Valid @RequestBody Suscriptor suscriptor, @PathVariable String cedula,
			BindingResult result) {

		Map<String, Object> response = new HashMap<String, Object>();
		Suscriptor suscriptoraux = suscriptorService.findByCedula(cedula);

		if (suscriptoraux == null) {
			response.put("mensaje", "El cliente con cédula ".concat(cedula.concat(" no se encontró")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			suscriptorService.save(suscriptor);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al hacer registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Suscriptor actualizado con éxito");
		response.put("suscriptor", suscriptor);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/suscriptores/{cedula}")
	public ResponseEntity<?> deleteSuscriptor(@PathVariable String cedula) {

		Map<String, Object> response = new HashMap<String, Object>();

		try {
			suscriptorService.deleteByCedula(cedula);
		} catch (DataAccessException e ) {
			response.put("mensaje", "Este suscriptor tiene predios asociados");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Suscriptor eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_FONTANERO","ROLE_TESORERO"})
	@GetMapping("/suscriptores/{cedula}/predios")
	public @ResponseBody List<Predio> getPrediosBySuscriptor(@PathVariable String cedula) {
		return suscriptorService.getPrediosBySuscriptor(cedula);
	}
	
	@GetMapping(value = "/suscriptores/reportes/todos", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<ByteArrayResource> generarReporte() {
		Path path = Paths.get(Utils.ALL_SUBSCRIBERS_REPORT_TEMPLATE).toAbsolutePath();
		try {
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(suscriptorService.obtenerTodosSuscriptores());
			
			JasperReport report = JasperCompileManager.compileReport(path.toString());
			JasperPrint print = JasperFillManager.fillReport(report, null, dataSource);

			byte[] pdfAsByteArray = JasperExportManager.exportReportToPdf(print);

			ByteArrayResource bar = new ByteArrayResource(pdfAsByteArray);

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=reporte_suscritores.pdf")
					.contentType(MediaType.APPLICATION_PDF) //
					.body(bar);
		} catch (JRException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping(value = "/suscriptores/reportes/enMora", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<ByteArrayResource> generarReporteSuscriptores() {
		Path path = Paths.get(Utils.SUBSCRIBERS_WITH_DEBT_ARREARS_REPORT_TEMPLATE).toAbsolutePath();
		try {
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(suscriptorService.obtenerSuscriptoresEnMora());
			
			JasperReport report = JasperCompileManager.compileReport(path.toString());
			
			Map<String,Object> parameters = new HashMap<>();
			
			parameters.put("deudaTotal", suscriptorService.obtenerGranTotalDeuda());
			
			
			JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);

			byte[] pdfAsByteArray = JasperExportManager.exportReportToPdf(print);

			ByteArrayResource bar = new ByteArrayResource(pdfAsByteArray);

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=reporte_suscritores_en_mora.pdf")
					.contentType(MediaType.APPLICATION_PDF) //
					.body(bar);
		} catch (JRException e) {
			e.printStackTrace();
		}
		return null;
	}

}
