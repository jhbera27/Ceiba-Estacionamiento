package com.ceiba.estacionamiento.controller;

import java.util.List;
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
	private VehiculoService VehiculoService;

	/**
	 * Método contructor para el controller
	 * 
	 * @param vehiculoRepository, el repository para realizar las transacciones
	 */
	public VehiculoController(VehiculoService VehiculoService) {
	   this.VehiculoService = VehiculoService;
	}

	/**
	 * servicio encargado del consultar todos los vehiculos que se encuentran
	 * parqueados
	 * 
	 * @return lista de vehiculos estacionado actualmente en el parqueadero
	 */
	@RequestMapping("/obtenerVehiculos")
	public List<Vehiculo> obtenerVehiculos() {
		return VehiculoService.obtenerVehiculos();
	}

	/**
	 * servicio encargado de registrar un vehiculo en el parqueadero
	 * 
	 * @param vehiculo, el vehiculo a registrar en el parqueadero
	 */
	@PostMapping("/crearVehiculo")
	public void crearVehiculo(@RequestBody Vehiculo vehiculo) {
		VehiculoService.agregar(vehiculo);
	}

}
