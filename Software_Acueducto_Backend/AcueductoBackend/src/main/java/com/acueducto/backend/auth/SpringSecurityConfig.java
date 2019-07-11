package com.acueducto.backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class SpringSecurityConfig  extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService empleadoService;
	
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.empleadoService)
		.passwordEncoder(getPasswordEncoder());
	}
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	//Permite implementar las reglas de seguridad para la aplicación
		@Override
		public void configure(HttpSecurity http) throws Exception {
			
			//Define como publico el nombre, para el resto de rutas debe estar autenticado
			http.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.csrf().disable()
			//Se deja stateless ya que se va a utilizar JWT
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}
}
