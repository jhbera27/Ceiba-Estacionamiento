package com.ceiba.estacinamiento.dominio;

import java.io.Serializable;
import java.util.Date;

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

	public Vehiculo(Long id, String placa, Date fechaIngreso, String tipo, Integer cilindraje,
			Boolean estaParqueado) {
		this.id = id;
		this.placa = placa;
		this.fechaIngreso = fechaIngreso;
		this.tipo = tipo;
		this.cilindraje = cilindraje;
		this.estaParqueado = estaParqueado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(Integer cilindraje) {
		this.cilindraje = cilindraje;
	}

	public Boolean getEstaParqueado() {
		return estaParqueado;
	}

	public void setEstaParqueado(Boolean estaParqueado) {
		this.estaParqueado = estaParqueado;
	}

}
