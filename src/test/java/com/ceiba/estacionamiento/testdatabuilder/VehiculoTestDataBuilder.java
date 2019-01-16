package com.ceiba.estacionamiento.testdatabuilder;

import java.util.Date;
import com.ceiba.estacinamiento.dominio.Vehiculo;

/**
 * clase para la creacion de objetos tipo vehiculo para las pruebas unitarias y
 * de integracion
 * 
 * @author jhon.bedoya
 *
 */
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

	/**
	 * metodo contructor de la clase VehiculoTestDataBuilder
	 */
	public VehiculoTestDataBuilder() {
		this.placa = PLACA;
		this.tipo = MOTO;
		this.cilindraje = CILINDRAGE;
		this.estaParqueado = Boolean.TRUE;
	}

	/**
	 * Método encargado de asiganarle un nuevo valor al atributo id del objeto
	 * VehiculoTestDataBuilder
	 * 
	 * @param id, el identificador del vehiculo
	 * @return VehiculoTestDataBuilder, el vehiculo data builder con el atributo
	 *         seteado
	 */
	public VehiculoTestDataBuilder conId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Método encargado de asiganarle un nuevo valor al atributo placa del objeto
	 * VehiculoTestDataBuilder
	 * 
	 * @param placa, la placa del vehiculo
	 * @return VehiculoTestDataBuilder, el vehiculo data builder con el atributo
	 *         seteado
	 */
	public VehiculoTestDataBuilder conPlaca(String placa) {
		this.placa = placa;
		return this;
	}

	/**
	 * Método encargado de asiganarle un nuevo valor al atributo fechaIngreso del
	 * objeto VehiculoTestDataBuilder
	 * 
	 * @param fechaIngreso, la fecha de ingreso del vehiculo al parqueadero
	 * @return VehiculoTestDataBuilder, el vehiculo data builder con el atributo
	 *         seteado
	 */
	public VehiculoTestDataBuilder conFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
		return this;
	}

	/**
	 * Método encargado de asiganarle un nuevo valor al atributo tipo del objeto
	 * VehiculoTestDataBuilder
	 * 
	 * @param tipo, moto o carro
	 * @return VehiculoTestDataBuilder, el vehiculo data builder con el atributo
	 *         seteado
	 */
	public VehiculoTestDataBuilder conTipo(String tipo) {
		this.tipo = tipo;
		return this;
	}

	/**
	 * Método encargado de asiganarle un nuevo valor al atributo cilindraje del
	 * objeto VehiculoTestDataBuilder
	 * 
	 * @param cilindraje, el cilindraje del vehiculo
	 * @return VehiculoTestDataBuilder, el vehiculo data builder con el atributo
	 *         seteado
	 */
	public VehiculoTestDataBuilder conCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
		return this;
	}

	/**
	 * Método encargado de asiganarle un nuevo valor al atributo estaParqueado del
	 * objeto VehiculoTestDataBuilder
	 * 
	 * @param estaParqueado, true si esta parqueado, false en caso contrario
	 * @return VehiculoTestDataBuilder, el vehiculo data builder con el atributo
	 *         seteado
	 */
	public VehiculoTestDataBuilder conEstaParqueado(Boolean estaParqueado) {
		this.estaParqueado = estaParqueado;
		return this;
	}

	/**
	 * Método encargado de construir el objeto vehiculo del dominio
	 * 
	 * @return Vehiculo, el vehiculo creado
	 */
	public Vehiculo build() {
		return new Vehiculo(this.id, this.placa, this.fechaIngreso, this.tipo, this.cilindraje, this.estaParqueado);
	}

}
