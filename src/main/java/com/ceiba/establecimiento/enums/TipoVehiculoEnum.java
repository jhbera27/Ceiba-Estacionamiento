package com.ceiba.establecimiento.enums;

/**
 * clase tipo enum para identificar los tipos de vehiculo (carro o moto)
 * 
 * @author jhon.bedoya
 *
 */
public enum TipoVehiculoEnum {
	/**
	 * tipo de vehiculo MOTO
	 */
	MOTO("TipoVehiculoEnum.MOTO"),
	/**
	 * tipo de vehiculo CARRO
	 */
	CARRO("TipoVehiculoEnum.CARRO");

	private final String descripcion;


	private TipoVehiculoEnum(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
