package com.ceiba.estacionamiento.testdatabuilder;

import java.util.Date;
import com.ceiba.estacinamiento.dominio.Vehiculo;

public class VehiculoTestDataBuilder {

	/**
	 * placa por defecto para del objeto VehiculoTestDataBuilder
	 */
	private static final String PLACA = "PQT29G";
	/**
	 * cilindraje por defecto del objeto VehiculoTestDataBuilder
	 */
	private static final int CILINDRAGE = 650;
	/**
	 * cilindraje por defecto del objeto VehiculoTestDataBuilder
	 */
	private static final String MOTO = "MOTO";

	/**
	 * atributo que determina el identificador del vehiculo
	 */
	private Long id;
	/**
	 * atributo que determina la placa del vehiculo
	 */
	private String placa;
	/**
	 * atributo que determina la fecha de ingreso del vehiculo
	 */
	private Date fechaIngreso;
	/**
	 * atributo que determina el tipo de vehiculo (carro o moto)
	 */
	private String tipo;
	/**
	 * atributo que determina el ciclindraje del vehiculo
	 */
	private Integer cilindraje;
	/**
	 * atributo que determina si el vehiculo se encuentra parqueado
	 */
	private Boolean estaParqueado;

	
	public VehiculoTestDataBuilder() {
		this.placa = PLACA;
		this.tipo = MOTO;
		this.cilindraje = CILINDRAGE;
		this.estaParqueado = Boolean.TRUE;
	}

	public VehiculoTestDataBuilder conId(Long id) {
		this.id = id;
		return this;
	}


	public VehiculoTestDataBuilder conPlaca(String placa) {
		this.placa = placa;
		return this;
	}


	public VehiculoTestDataBuilder conFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
		return this;
	}

	public VehiculoTestDataBuilder conTipo(String tipo) {
		this.tipo = tipo;
		return this;
	}

	public VehiculoTestDataBuilder conCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
		return this;
	}


	public VehiculoTestDataBuilder conEstaParqueado(Boolean estaParqueado) {
		this.estaParqueado = estaParqueado;
		return this;
	}


	public Vehiculo build() {
		return new Vehiculo(this.id, this.placa, this.fechaIngreso, this.tipo, this.cilindraje, this.estaParqueado);
	}

}
