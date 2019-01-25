package com.ceiba.estacinamiento.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SalidaVehiculo implements Serializable {
	/**
	 * atributo para la serializacion
	 */
	private static final long serialVersionUID = 1L;
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
	/**
	 * constructor para la serializacion
	 */
	public SalidaVehiculo() {
		
	}

	public SalidaVehiculo(Long id, Vehiculo vehiculoSalida, BigDecimal precioPagado, Date fechaSalida) {
		this.id = id;
		this.vehiculoSalida = vehiculoSalida;
		this.precioPagado = precioPagado;
		this.fechaSalida = fechaSalida;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Vehiculo getVehiculoSalida() {
		return vehiculoSalida;
	}

	public void setVehiculoSalida(Vehiculo vehiculoSalida) {
		this.vehiculoSalida = vehiculoSalida;
	}

	public BigDecimal getPrecioPagado() {
		return precioPagado;
	}

	public void setPrecioPagado(BigDecimal precioPagado) {
		this.precioPagado = precioPagado;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
}
