package com.ceiba.estacionamiento.estacionamiento;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * clase encargada de la configuracion de los servicios rest para permitir el el
 * control de acceso HTTP
 * 
 * @author jhon.bedoya
 *
 */
@Configuration
public class RestConfig {
	/**
	 * método encargado de la configuracion de los servicios rest para permitir el
	 * el control de acceso HTTP
	 * 
	 * @return  la configuracion CORS para la aaplicacion
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}