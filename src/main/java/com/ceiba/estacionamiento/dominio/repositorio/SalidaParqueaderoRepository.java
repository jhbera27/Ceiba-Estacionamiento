package com.ceiba.estacionamiento.dominio.repositorio;

import java.math.BigDecimal;
import java.util.List;

import com.ceiba.estacinamiento.dominio.SalidaParqueadero;
import com.ceiba.estacinamiento.dominio.Vehiculo;

/**
 * interface que expone los servicios a crear para la salida del parqueadero
 * 
 * @author jhon.bedoya
 *
 */
public interface SalidaParqueaderoRepository {

	/**
	 * Permite registrar una salida del parqueadero
	 * 
	 * @param salidaParqueadero, la salida del vehiculo del parqueadero
	 */
	void agregar(SalidaParqueadero salidaParqueadero);

	/**
	 * encargado de calcular el precio total a pagar teniendo en cuenta las reglas
	 * de negocio de tipo de vehiculo, numero de horas ,valor hora y dia
	 * 
	 * @param vehiculoSalida, el vehiculo que va a salir del parqueadero
	 * @param valorHoraVehiculo, valor de una hora en el parqueadero
	 * @param valorDiaVehiculo, valor de un dia en el parqueadero
	 * @return el precio total a pagar para el vehiculo
	 */
	BigDecimal calcularPrecioAPagar(Vehiculo vehiculoSalida, int valorHoraVehiculo, int valorDiaVehiculo);
	/**
	 * encargado de consultar el historia de vehiculos que utilizaron el
	 * parqueadero
	 * 
	 * @return la lista de SalidaParqueadero
	 */
	List<SalidaParqueadero> obtenerSalidaVehiculosParqueadero();

}
