package com.acueducto.backend.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.acueducto.backend.models.entity.Usuario;
import com.acueducto.backend.services.IUsuarioService;

/**
 * Agrega campos al token
 * 
 *
 */
@Component
public class ExtraTokenInfo implements TokenEnhancer {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		//Obtiene el nombre del usuario del objeto authentication
		Usuario usuario = usuarioService.findByUsuario(authentication.getName());
		
		
		Map<String,Object> extraInfo = new HashMap<String,Object>();
		
		extraInfo.put("usuario", usuario.getUsuario());
		extraInfo.put("foto", usuario.getFoto());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(extraInfo);
		return accessToken;
	}

}
