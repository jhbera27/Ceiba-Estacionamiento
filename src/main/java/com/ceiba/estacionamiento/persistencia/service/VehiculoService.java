package com.ceiba.estacionamiento.persistencia.service;

import java.util.List;

import com.ceiba.estacinamiento.dominio.Vehiculo;

public interface VehiculoService {
	
	List<Vehiculo> obtenerVehiculos();
	
	void agregar(Vehiculo vehiculo);
	
	Vehiculo buscarVehiculoPorPlaca(String placa);

}
