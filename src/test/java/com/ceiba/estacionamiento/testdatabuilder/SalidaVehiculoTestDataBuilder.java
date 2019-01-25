package com.ceiba.estacionamiento.testdatabuilder;

import java.math.BigDecimal;
import java.util.Date;

import com.ceiba.estacinamiento.dominio.SalidaVehiculo;
import com.ceiba.estacinamiento.dominio.Vehiculo;


public class SalidaVehiculoTestDataBuilder {
	/**
	 * precio pagado por defecto del objeto SalidaParqueaderoTestDataBuilder
	 */
	private static final BigDecimal PRECIO_PAGADO = new BigDecimal(500000);
	/**
	 * vehiculo salida por defecto del objeto SalidaParqueaderoTestDataBuilder
	 */
	private static final Vehiculo VEHICULO = new VehiculoTestDataBuilder().build();
	/**
	 * atributo que determina el identificador de la salida de un vehiculo del
	 * parqueadero
	 */
	private Long id;
	/**
	 * atributo que determina el vehiculo que salio del parqueadero
	 */
	private Vehiculo vehiculoSalida;
	/**
	 * atributo que determina el precio total pagado por el vehiculo
	 */
	private BigDecimal precioPagado;
	/**
	 * atributo que determina la fecha de salida del vehiculo del parqueadero
	 */
	private Date fechaSalida;


	public SalidaVehiculoTestDataBuilder() {
		this.precioPagado = PRECIO_PAGADO;
		this.vehiculoSalida = VEHICULO;
	}


	public SalidaVehiculoTestDataBuilder conId(Long id) {
		this.id = id;
		return this;
	}

	public SalidaVehiculoTestDataBuilder conVehiculoSalida(Vehiculo vehiculo) {
		this.vehiculoSalida = vehiculo;
		return this;
	}
	

	public SalidaVehiculoTestDataBuilder conPrecioPagado(BigDecimal precioPagado) {
		this.precioPagado = precioPagado;
		return this;
	}
	
	public SalidaVehiculoTestDataBuilder conFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
		return this;
	}
	

	public SalidaVehiculo build() {
		return new SalidaVehiculo(id, vehiculoSalida, precioPagado, fechaSalida);
	}

}
