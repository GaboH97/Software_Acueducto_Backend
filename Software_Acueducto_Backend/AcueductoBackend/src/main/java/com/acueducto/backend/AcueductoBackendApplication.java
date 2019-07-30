package com.acueducto.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.acueducto.backend.models.dao.ILugarDAO;

@SpringBootApplication
public class AcueductoBackendApplication implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ILugarDAO iLugarDAO;

	public static void main(String[] args) {
		SpringApplication.run(AcueductoBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		iLugarDAO.obtenerReporteRecaudosVereda().forEach(r ->{
			System.out.println(r.getNombreVereda());
			System.out.println(r.getTotalRecaudo());

		});
	}

}
