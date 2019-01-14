package com.ceiba.estacionamiento.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceiba.estacinamiento.dominio.SalidaParqueadero;
import com.ceiba.estacionamiento.dominio.repositorio.SalidaParqueaderoRepository;

/**
 * clase que expone los servicios a consumir para la salida del parqueadero
 * 
 * @author jhon.bedoya
 *
 */
@RestController
@RequestMapping("/salidaParqueadero")
public class SalidaParquedaeroController {

	SalidaParqueaderoRepository salidaParqueaderoRepository;

	/**
	 * Método constructor de la clase
	 * 
	 * @param salidaParqueaderoRepository, el repository para realizar la
	 *        transaccion
	 */
	public SalidaParquedaeroController(SalidaParqueaderoRepository salidaParqueaderoRepository) {
		this.salidaParqueaderoRepository = salidaParqueaderoRepository;
	}

	/**
	 * servicio encargado de registrar la salida de un vehiculo del parqueadero
	 * 
	 * @param salidaParqueadero, la informacion relacionada con la salida de un
	 *        vehiculo del parqueadero
	 */
	@PostMapping("/crearSalidaParqueadero")
	public void crearSalidaParqueadero(@RequestBody SalidaParqueadero salidaParqueadero) {
		salidaParqueaderoRepository.agregar(salidaParqueadero);
	}
}
