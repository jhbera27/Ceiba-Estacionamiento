package com.ceiba.estacionamiento.persistencia.builder;

import java.util.Date;

import com.ceiba.establecimiento.enums.TipoVehiculoEnum;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.persistencia.entity.VehiculoEntity;

/**
 * Clase encargada de realizar los build de dominio a entity y viseversa para un
 * vehiculo
 * 
 * @author jhon.bedoya
 *
 */
public final class VehiculoBuilder {
	/**
	 * Método constructor del builder
	 */
	private VehiculoBuilder() {
	}

	/**
	 * Método encargado de convertir un vehiculo de entity a dominio
	 * 
	 * @param vehiculoEntity, el vehiculo entity a convertir
	 * @return Vehiculo, el vehiculo convertido a dominio
	 */
	public static Vehiculo convertirADominio(VehiculoEntity vehiculoEntity) {

		Vehiculo vehiculo = null;

		if (vehiculoEntity != null) {
			vehiculo = new Vehiculo(vehiculoEntity.getId(), vehiculoEntity.getPlaca(), vehiculoEntity.getFechaIngreso(),
					vehiculoEntity.getTipo().name(), vehiculoEntity.getCilindraje(), vehiculoEntity.getEstaParqueado());

		}
		return vehiculo;
	}

	/**
	 * Método encargado de convertir un vehiculo de dominio a entity
	 * 
	 * @param vehiculo, el vehiculo de dominio a convertir
	 * @return VehiculoEntity. el vehiculo de dominio convertido a entity
	 */
	public static VehiculoEntity convertirAEntity(Vehiculo vehiculo) {

		VehiculoEntity vehiculoEntity = new VehiculoEntity();

		vehiculoEntity.setId(vehiculo.getId());
		vehiculoEntity.setPlaca(vehiculo.getPlaca());
		vehiculoEntity.setFechaIngreso(vehiculo.getFechaIngreso() != null ? vehiculo.getFechaIngreso() : new Date());
		vehiculoEntity.setTipo(TipoVehiculoEnum.valueOf(vehiculo.getTipo()));
		vehiculoEntity.setCilindraje(vehiculo.getCilindraje());
		vehiculoEntity.setEstaParqueado(vehiculo.getEstaParqueado());

		return vehiculoEntity;
	}

}
