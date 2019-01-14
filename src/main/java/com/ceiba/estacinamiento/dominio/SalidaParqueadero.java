package com.ceiba.estacinamiento.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * clase que representa el objeto SalidaParqueadero para el dominio
 * 
 * @author jhon.bedoya
 *
 */
public class SalidaParqueadero implements Serializable {
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
	 * Método contructor para el SalidaParqueadero del dominio
	 * 
	 * @param id, el identificador de la salida del vehiculo del parqueadero
	 * @param vehiculoSalida, el vehiculo que salio del parqueadero
	 * @param precioPagado, el precio pagado por el vehiculo
	 * @param fechaSalida, la fecha de salida del vehiculo del parqueadero
	 */
	public SalidaParqueadero(Long id, Vehiculo vehiculoSalida, BigDecimal precioPagado, Date fechaSalida) {
		this.id = id;
		this.vehiculoSalida = vehiculoSalida;
		this.precioPagado = precioPagado;
		this.fechaSalida = fechaSalida;

	}

	/**
	 * método encargado de obtener el identificador de la salida del vehiculo del
	 * parqueadero
	 * 
	 * @return id, el identificador de la salida
	 */
	public Long getId() {
		return id;
	}

	/**
	 * método en cargado de setear el identificador de la salida del vehiculo del
	 * parqueadero
	 * 
	 * @param id, el nuevo identificador de la salida del vehiculo
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * método encargado de obtener el vehiculo que salio del parqueadero
	 * 
	 * @return vehiculoSalida, el vehiculo que salio del parqueadero
	 */
	public Vehiculo getVehiculoSalida() {
		return vehiculoSalida;
	}

	/**
	 * método en cargado de setear el vehiculo que salio del parqueadero
	 * 
	 * @param vehiculoSalida, el nuevo vehiculo de salida del parqueadero
	 */
	public void setVehiculoSalida(Vehiculo vehiculoSalida) {
		this.vehiculoSalida = vehiculoSalida;
	}

	/**
	 * método encargado de obtener el precio pagado por le vehiculo
	 * 
	 * @return precioPagado, el precio pagado por el vehiculo
	 */
	public BigDecimal getPrecioPagado() {
		return precioPagado;
	}

	/**
	 * método en cargado de setear el precio pagado por el vehiculo que salio del
	 * parqueadero
	 * 
	 * @param precioPagado, el nuevo precio pagado por el vehiculo
	 */
	public void setPrecioPagado(BigDecimal precioPagado) {
		this.precioPagado = precioPagado;
	}

	/**
	 * método encargado de obtener la fecha de salida del vehiculo del parqueadero
	 * 
	 * @return fechaSalida, la fecha de salida del vehiculo
	 */
	public Date getFechaSalida() {
		return fechaSalida;
	}

	/**
	 * método en cargado de setear la fecha de salida del vehiculo del parqueadero
	 * 
	 * @param fechaSalida, la nueva fecha de salida del parqueadero
	 */
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
}
