package com.ceiba.estacionamiento.testdatabuilder;

import com.ceiba.estacinamiento.dominio.CalcularPrecio;
import com.ceiba.estacinamiento.dominio.Trm;
import com.ceiba.estacinamiento.dominio.Vehiculo;

public class CalcularPrecioTestDataBuilder {
	/**
	 * atributo que representa el valor de una hora para una moto en el parqueadero
	 */
	public static final int VALOR_HORA_MOTO = 500;
	/**
	 * atributo que representa el valor de un dia para un carro en el parqueadero
	 */
	public static final int VALOR_DIA_MOTO = 4000;

	/**
	 * atributo que determina el vehiculo que salio del parqueadero
	 */
	private Vehiculo vehiculoSalida;
	/**
	 * atributo que determina el valor de la hora del vehiculo
	 */
	private int valorHoraVehiculo;
	/**
	 * atributo que determina el valor del del vehiculo
	 */
	private int valorDiaVehiculo;
	
	public CalcularPrecioTestDataBuilder() {
		this.vehiculoSalida = new VehiculoTestDataBuilder().build();
		this.valorHoraVehiculo = VALOR_HORA_MOTO;
		this.valorDiaVehiculo = VALOR_DIA_MOTO;
	}
	
	/**
	 * Método encargado de construir el objeto CalcularPrecio del dominio
	 * 
	 * @return CalcularPrecio, el objeto CalcularPrecio creado
	 */
	public CalcularPrecio build() {
		return new CalcularPrecio(this.vehiculoSalida, this.valorHoraVehiculo, this.valorDiaVehiculo);
	}

}
