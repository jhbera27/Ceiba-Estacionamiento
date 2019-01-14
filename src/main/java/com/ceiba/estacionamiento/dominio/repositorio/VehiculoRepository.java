package com.ceiba.estacionamiento.dominio.repositorio;

import java.util.List;

import com.ceiba.estacinamiento.dominio.Vehiculo;

/**
 * 
 * @author jhon.bedoya
 *
 */
public interface VehiculoRepository {

	/**
	 * Permite obtener los vehiculos que estan estacionados en el parqueadero
	 * 
	 * @return la lista de vehuclos que se encuentran parqueados
	 */
	List<Vehiculo> obtenerVehiculos();

	/**
	 * Permite agregar un vehiculo al parqueadero
	 * 
	 * @param vehiculo, el vehiculo que se va a estacionar en el parqueadero
	 */
	void agregar(Vehiculo vehiculo);
	
	
    /**
     * Método encargado de buscar un vehiculo por placa
     * @param placa, la placa del vehiculo a buscar
     */
	Vehiculo buscarVehiculoPorPlaca(String placa);

}
