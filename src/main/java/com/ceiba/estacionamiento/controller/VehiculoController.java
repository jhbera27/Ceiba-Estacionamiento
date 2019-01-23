package com.ceiba.estacionamiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.persistencia.service.VehiculoService;

/**
 * clase que expone los servicios a consumir para el vehiculo
 * 
 * @author jhon.bedoya
 *
 */
@RestController
@RequestMapping("/vehiculo")
public class VehiculoController {

	/**
	 * Atributo que determina el repositorio de vehiculo 
	 */
	@Autowired
	private VehiculoService vehiculoService;

	/**
	 * Método contructor para el controller
	 * 
	 * @param vehiculoRepository, el repository para realizar las transacciones
	 */
	public VehiculoController(VehiculoService vehiculoService) {
	   this.vehiculoService = vehiculoService;
	}

	/**
	 * servicio encargado del consultar todos los vehiculos que se encuentran
	 * parqueados
	 * 
	 * @return lista de vehiculos estacionado actualmente en el parqueadero
	 */
	@RequestMapping("/obtenerVehiculos")
	public List<Vehiculo> obtenerVehiculos() {
		return vehiculoService.obtenerVehiculos();
	}

	/**
	 * servicio encargado de registrar un vehiculo en el parqueadero
	 * 
	 * @param vehiculo, el vehiculo a registrar en el parqueadero
	 */
	@PostMapping("/crearVehiculo")
	public void crearVehiculo(@RequestBody Vehiculo vehiculo) {
		vehiculoService.agregar(vehiculo);
	}

}
