package com.ceiba.estacionamiento.estacionamiento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * clase encargada de la inizializacion del proyecto
 * 
 * @author jhon.bedoya
 *
 */
@SpringBootApplication()
@EnableConfigurationProperties
@EntityScan(basePackages = { "com.ceiba.estacionamiento.persistencia.entity" }) // scan JPA entities
public class EstacionamientoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstacionamientoApplication.class, args);
	}

}
