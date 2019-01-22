package com.ceiba.estacionamiento.persistencia.service;

import java.math.BigDecimal;
import java.util.List;
import com.ceiba.estacinamiento.dominio.SalidaParqueadero;
import com.ceiba.estacinamiento.dominio.Vehiculo;

public interface SalidaParqueaderoService {
	
	void agregar(SalidaParqueadero salidaParqueadero);
	
	List<SalidaParqueadero> obtenerSalidaVehiculosParqueadero();
	
	BigDecimal calcularPrecioAPagar(Vehiculo vehiculoSalida, int valorHoraVehiculo, int valorDiaVehiculo);

}
