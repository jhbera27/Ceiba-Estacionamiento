package com.ceiba.estacionamiento.controller;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceiba.estacinamiento.dominio.CalcularPrecio;
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
	/**
	 * Atributo para manejar el repositorio de salida de vehiculos del parqueadero
	 */
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
    /**
     * Servicio encargado de consultar el precio a pagar por el vehiculo 
     * @param calcularPrecio, informacion requerida para calcular el precio a pagar
     * @return el total a pagar por el vehiculo
     */
	@PostMapping("/calcularValor")
	public BigDecimal calcularValor(@RequestBody CalcularPrecio calcularPrecio) {
		return salidaParqueaderoRepository.calcularPrecioAPagar(calcularPrecio.getVehiculoSalida(),
				calcularPrecio.getValorHoraVehiculo(), calcularPrecio.getValorDiaVehiculo());
	}
    
	/**
	 * servicio para consultar la lista de vehiculos que han salido del parqueadero
	 * @return lista de vehiculos que han salido del parqueadero
	 */
	@RequestMapping("/obtenerSalidas")
	public List<SalidaParqueadero> obtenerSalidas() {
		return salidaParqueaderoRepository.obtenerSalidaVehiculosParqueadero();
	}
}
