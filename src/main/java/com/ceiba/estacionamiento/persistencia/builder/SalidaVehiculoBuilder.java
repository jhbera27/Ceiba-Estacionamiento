package com.ceiba.estacionamiento.persistencia.builder;

import java.util.Date;

import com.ceiba.estacinamiento.dominio.SalidaVehiculo;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.persistencia.entity.SalidaVehiculoEntity;
import com.ceiba.estacionamiento.persistencia.entity.VehiculoEntity;


public final class SalidaVehiculoBuilder {
	/**
	 * Método constructor del builder
	 */
	private SalidaVehiculoBuilder() {

	}

	public static SalidaVehiculo convertirADominio(SalidaVehiculoEntity salidaParqueaderoEntity) {
		SalidaVehiculo salidaParqueadero = null;
		Vehiculo vehiculo = VehiculoBuilder.convertirADominio(salidaParqueaderoEntity.getVehiculoSalida());
		salidaParqueadero = new SalidaVehiculo(salidaParqueaderoEntity.getId(), vehiculo,
				salidaParqueaderoEntity.getPrecioPagado(), salidaParqueaderoEntity.getFechaSalida());

		return salidaParqueadero;
	}

	public static SalidaVehiculoEntity convertirAEntity(SalidaVehiculo salidaParqueadero) {
		SalidaVehiculoEntity salidaParqueaderoEntity = new SalidaVehiculoEntity();
		VehiculoEntity vehiculoEntity = VehiculoBuilder.convertirAEntity(salidaParqueadero.getVehiculoSalida());
		salidaParqueaderoEntity.setId(salidaParqueadero.getId());
		salidaParqueaderoEntity.setPrecioPagado(salidaParqueadero.getPrecioPagado());
		salidaParqueaderoEntity.setVehiculoSalida(vehiculoEntity);
		salidaParqueaderoEntity.setFechaSalida(new Date());

		return salidaParqueaderoEntity;
	}
}
