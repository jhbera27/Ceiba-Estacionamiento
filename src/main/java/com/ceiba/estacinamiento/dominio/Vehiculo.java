package com.ceiba.estacinamiento.dominio;

import java.io.Serializable;
import java.util.Date;

/**
 * clase que representa el objeto vehiculo para el dominio
 * 
 * @author jhon.bedoya
 *
 */
public class Vehiculo implements Serializable {
	/**
	 * atributo para la serializacion
	 */
	private static final long serialVersionUID = 1L;
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
	private int cilindraje;
	/**
	 * atributo que determina si el vehiculo se encuentra parqueado
	 */
	private Boolean estaParqueado;
	/**
	 * Constructo para la serializacion
	 */
	public Vehiculo() {
		
	}

	/**
	 * Método constructor del vehiculo para el dominio
	 * 
	 * @param id, el identificador del vehiculo
	 * @param placa, la placa del vehiculo
	 * @param fechaIngreso, la fecha de ingreso del vehiculo
	 * @param tipo, el tipo de vehiculo (carro o moto)
	 * @param cilindraje, el cilindraje del vehiculo
	 * @param estaParqueado, si se encuentra parqueado true, false en caso contrario
	 */
	public Vehiculo(Long id, String placa, Date fechaIngreso, String tipo, Integer cilindraje,
			Boolean estaParqueado) {
		this.id = id;
		this.placa = placa;
		this.fechaIngreso = fechaIngreso;
		this.tipo = tipo;
		this.cilindraje = cilindraje;
		this.estaParqueado = estaParqueado;
	}

	/**
	 * método encargado de obtener el identificador de un vehiculo
	 * 
	 * @return id, el identificador del vehiculo
	 */
	public Long getId() {
		return id;
	}

	/**
	 * método encargado de setear el identificador del vehiculo
	 * 
	 * @param id, el nuevo identificador del vehiculo
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * método encargado de obtener la placa de un vehiculo
	 * 
	 * @return placa, la placa del vehiculo
	 */
	public String getPlaca() {
		return placa;
	}

	/**
	 * método encargado de setear la placa del vehiculo
	 * 
	 * @param placa, la nueva placa del vehiculo
	 */
	public void setPlaca(String placa) {
		this.placa = placa;
	}

	/**
	 * método encargado de obtener la fecha de ingreso de un vehiculo
	 * 
	 * @return fechaIngreso, la fecha de ingreso del vehiculo
	 */
	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	/**
	 * método encargado de setear la fecha de ingreso del vehiculo
	 * 
	 * @param fechaIngreso, la nueva fecha de ingreso del vehiculo
	 */
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	/**
	 * método encargado de obtener el tipo de vehiculo
	 * 
	 * @return tipo, el tipo de vehiculo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * método encargado de setear el tipo de vehiculo
	 * 
	 * @param tipo, el nuevo tipo de vehiculo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * método encargado de obtener el cilindraje del vehiculo
	 * 
	 * @return cilindraje, el cilindraje del vehiculo
	 */
	public Integer getCilindraje() {
		return cilindraje;
	}

	/**
	 * método encargado de setear el cilindraje de vehiculo
	 * 
	 * @param cilindraje, el nuevo cilindraje del vehiculo
	 */
	public void setCilindraje(Integer cilindraje) {
		this.cilindraje = cilindraje;
	}

	/**
	 * método encargado de identificar si el vehiculo se encuentra parqueado
	 * 
	 * @return estaParqueado, true si esta parqueado, false en caso contrario
	 */
	public Boolean getEstaParqueado() {
		return estaParqueado;
	}

	/**
	 * método encargado de definir si el vehiculo se encuentra parqueado
	 * 
	 * @param estaParqueado, true si se encuentra parqueado, false en caso contrario
	 */
	public void setEstaParqueado(Boolean estaParqueado) {
		this.estaParqueado = estaParqueado;
	}

}
