package com.ceiba.estacionamiento.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceiba.estacinamiento.dominio.CalcularPrecio;
import com.ceiba.estacinamiento.dominio.SalidaVehiculo;
import com.ceiba.estacionamiento.persistencia.service.SalidaVehiculoService;


@RestController
@RequestMapping("/salidaVehiculo")
public class SalidaVehiculoController {

	
	@Autowired
	private SalidaVehiculoService salidaParqueaderoService;


	public SalidaVehiculoController(SalidaVehiculoService salidaParqueaderoService) {
		this.salidaParqueaderoService = salidaParqueaderoService;
	}

	@PostMapping("/registrarSalidaVehiculo")
	public void registrarSalidaVehiculo(@RequestBody SalidaVehiculo salidaParqueadero) {
		salidaParqueaderoService.registrarSalidaVehiculo(salidaParqueadero);
	}
 
	
	@PostMapping("/calcularPrecioAPagar")
	public BigDecimal calcularPrecioAPagar(@RequestBody CalcularPrecio calcularPrecio) {
		return salidaParqueaderoService.calcularPrecioAPagar(calcularPrecio.getVehiculoSalida(),
				calcularPrecio.getValorHoraVehiculo(), calcularPrecio.getValorDiaVehiculo());
	}
    
	@RequestMapping("/obtenerHistorialVehiculos")
	public List<SalidaVehiculo> obtenerHistorialVehiculos() {
		return salidaParqueaderoService.obtenerHistorialVehiculos();
	}
}
