package com.acueducto.backend.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	@Autowired
	private ExtraTokenInfo extraTokenInfo;

	@Override
	// Se encarga del proceso de autenticación y validación de token
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(extraTokenInfo, accessTokenConverter()));
		// Inyecta el authenticationManager

		endpoints.authenticationManager(authenticationManager)
				// Almacena los datos de autenticación del usuario
				// Decodifica el JWT para que el authenticationManager pueda autenticar al
				// usuario
				.accessTokenConverter(accessTokenConverter())
				.tokenEnhancer(tokenEnhancerChain);
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		// JwtAccessTokenConverter es una implementación de accessTokenConverter
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		return jwtAccessTokenConverter;
	}

	@Override
	// Configura los permisos de los end points
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

		// Da permiso a cualquier usuario de autenticarse
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		// Cliente para aplicación front-end angular
		clients.inMemory().withClient("acueducto").secret(passwordEncoder.encode("12345")).scopes("read", "write")
				.authorizedGrantTypes("password", "refresh_token")
				// Validez del token de una hora
				.accessTokenValiditySeconds(3600).refreshTokenValiditySeconds(3600);
	}

}
