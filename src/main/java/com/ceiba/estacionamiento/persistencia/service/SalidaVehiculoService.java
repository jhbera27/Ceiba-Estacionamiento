package com.ceiba.estacionamiento.persistencia.service;

import java.math.BigDecimal;
import java.util.List;
import com.ceiba.estacinamiento.dominio.SalidaVehiculo;
import com.ceiba.estacinamiento.dominio.Vehiculo;

public interface SalidaVehiculoService {
	
	void registrarSalidaVehiculo(SalidaVehiculo salidaParqueadero);
	
	List<SalidaVehiculo> obtenerHistorialVehiculos();
	
	BigDecimal calcularPrecioAPagar(Vehiculo vehiculoSalida, int valorHoraVehiculo, int valorDiaVehiculo);

}
