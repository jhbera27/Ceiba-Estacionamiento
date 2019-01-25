package com.ceiba.estacionamiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.persistencia.service.VehiculoService;

@RestController
@RequestMapping("/vehiculo")
public class VehiculoController {

	@Autowired
	private VehiculoService vehiculoService;

	public VehiculoController(VehiculoService vehiculoService) {
	   this.vehiculoService = vehiculoService;
	}

	@RequestMapping("/obtenerVehiculos")
	public List<Vehiculo> obtenerVehiculos() {
		return vehiculoService.obtenerVehiculos();
	}

	@PostMapping("/agregarVehiculoParqueadero")
	public void agregarVehiculoParqueadero(@RequestBody Vehiculo vehiculo) {
		vehiculoService.agregarVehiculoParqueadero(vehiculo);
	}

}
