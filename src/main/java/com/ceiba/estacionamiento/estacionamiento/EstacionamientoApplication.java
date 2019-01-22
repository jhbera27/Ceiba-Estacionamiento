package com.ceiba.estacionamiento.estacionamiento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * clase encargada de la inizializacion del proyecto
 * 
 * @author jhon.bedoya
 *
 */
@SpringBootApplication()
@ComponentScan({ "com.ceiba.estacionamiento.controller", "com.ceiba.estacionamiento.persistencia.repositorio",
		"com.ceiba.estacionamiento.persistencia.service", "com.ceiba.estacionamiento.persistencia.service.impl",
		"com.ceiba.estacionamiento.estacionamiento" })
@EnableConfigurationProperties
@EntityScan(basePackages = { "com.ceiba.estacionamiento.persistencia.entity" })
@EnableJpaRepositories({ "com.ceiba.estacionamiento.persistencia.repositorio" })
public class EstacionamientoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstacionamientoApplication.class, args);
	}

}
