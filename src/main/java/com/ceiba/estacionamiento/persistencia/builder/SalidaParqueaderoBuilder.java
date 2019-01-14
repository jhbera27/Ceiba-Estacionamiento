package com.ceiba.estacionamiento.persistencia.builder;

import java.util.Date;

import com.ceiba.estacinamiento.dominio.SalidaParqueadero;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.persistencia.entity.SalidaParqueaderoEntity;
import com.ceiba.estacionamiento.persistencia.entity.VehiculoEntity;

/**
 * Clase encargada de realizar los build de dominio a entity y viseversa para
 * una salida del paruqadero
 * 
 * @author jhon.bedoya
 *
 */
public class SalidaParqueaderoBuilder {
	/**
	 * Método constructor del builder
	 */
	private SalidaParqueaderoBuilder() {

	}

	/**
	 * Método encargado de convertir una salida de parueadero entity a dominio
	 * 
	 * @param salidaParqueaderoEntity, la entity a convertir a dominio
	 * @return SalidaParqueadero, la entity convertida a dominio
	 */
	public static SalidaParqueadero convertirADominio(SalidaParqueaderoEntity salidaParqueaderoEntity) {
		SalidaParqueadero salidaParqueadero = null;

		if (salidaParqueaderoEntity != null) {
			Vehiculo vehiculo = VehiculoBuilder.convertirADominio(salidaParqueaderoEntity.getVehiculoSalida());
			salidaParqueadero = new SalidaParqueadero(salidaParqueaderoEntity.getId(), vehiculo,
					salidaParqueaderoEntity.getPrecioPagado(), salidaParqueaderoEntity.getFechaSalida());
		}

		return salidaParqueadero;
	}

	/**
	 * Método encargado de convertir una salida de parqueadero de dominio a entity
	 * 
	 * @param salidaParqueadero, el objeto de dominio a convertir
	 * @return SalidaParqueaderoEntity, la entoy convertida a salida de parqueadero
	 *         de dominio
	 */
	public static SalidaParqueaderoEntity convertirAEntity(SalidaParqueadero salidaParqueadero) {
		SalidaParqueaderoEntity salidaParqueaderoEntity = new SalidaParqueaderoEntity();

		VehiculoEntity vehiculoEntity = VehiculoBuilder.convertirAEntity(salidaParqueadero.getVehiculoSalida());
		salidaParqueaderoEntity.setId(salidaParqueadero.getId());
		salidaParqueaderoEntity.setPrecioPagado(salidaParqueadero.getPrecioPagado());
		salidaParqueaderoEntity.setVehiculoSalida(vehiculoEntity);
		salidaParqueaderoEntity.setFechaSalida(new Date());

		return salidaParqueaderoEntity;
	}
}
