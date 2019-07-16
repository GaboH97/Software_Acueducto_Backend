package com.acueducto.backend.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.acueducto.backend.services.IFacturaService;
import com.acueducto.backend.services.ISuscriptorService;
import com.acueducto.backend.services.IUsuarioService;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class DashBoardController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IFacturaService facturaService;
	
	@Autowired
	private ISuscriptorService suscriptorService;
	
	@Secured({ "ROLE_ADMIN", "ROLE_FONTANERO", "ROLE_TESORERO" })
	@GetMapping("dashboard/summaryInfo")
	public ResponseEntity<?> getSummaryInfo(){
		Map<String, Object> response = new HashMap<String, Object>();
		
		response.put("usuariosActivos", usuarioService.obtenerNumeroUsuariosActivos());
		response.put("carteraPendiente", facturaService.obtenerValorCarteraPendiente());
		response.put("totalRecaudado", facturaService.obtenerTotalRecaudado());
		response.put("suscriptoresActivos",suscriptorService.obtenerNumeroSuscriptoresActivos());

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
}
