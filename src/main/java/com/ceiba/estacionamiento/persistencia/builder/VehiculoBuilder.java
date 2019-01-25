package com.ceiba.estacionamiento.persistencia.builder;

import java.util.Date;

import com.ceiba.establecimiento.enums.TipoVehiculoEnum;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.persistencia.entity.VehiculoEntity;


public final class VehiculoBuilder {

	private VehiculoBuilder() {
	}


	public static Vehiculo convertirADominio(VehiculoEntity vehiculoEntity) {
		Vehiculo vehiculo = null;
		vehiculo = new Vehiculo(vehiculoEntity.getId(), vehiculoEntity.getPlaca(), vehiculoEntity.getFechaIngreso(),
				vehiculoEntity.getTipo().name(), vehiculoEntity.getCilindraje(), vehiculoEntity.getEstaParqueado());

		return vehiculo;
	}

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
