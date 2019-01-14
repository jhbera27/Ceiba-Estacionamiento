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

	/**
	 * contructor de la enumeracion
	 * 
	 * @param descripcion, descripción del tipo de vehiculo
	 */
	private TipoVehiculoEnum(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * metodo encargado de obtener la descripcion de la enumeracion
	 * 
	 * @return descripcion, la descripcion de la enumeracion
	 */
	public String getDescripcion() {
		return descripcion;
	}

}
