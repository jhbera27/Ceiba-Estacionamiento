package com.ceiba.estacionamiento.persistencia.entity;

/**
 * Clase que representa el entity para una salida de un vehiculo en el parqueadero
 * 
 * @author jhon.bedoya
 *
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SALIDA_VEHICULO")
public class SalidaVehiculoEntity implements Serializable {

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
	private VehiculoEntity vehiculoSalida;
	/**
	 * atributo que determina el precio total pagado por el vehiculo
	 */
	private BigDecimal precioPagado;
	/**
	 * atributo que determina la fecha de salida del vehiculo del parqueadero
	 */
	private Date fechaSalida;

	/**
	 * método encargado de obtener el identificador de la salida del vehiculo del
	 * parqueadero
	 * 
	 * @return id, el identificador de la salida
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	public Long getId() {
		return id;
	}

	/**
	 *  método en cargado de setear el identificador de la salida del vehiculo del
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
	@ManyToOne
	@JoinColumn(name = "ID_VEHICULO", referencedColumnName = "ID", nullable = false)
	public VehiculoEntity getVehiculoSalida() {
		return vehiculoSalida;
	}

	/**
	 * método en cargado de setear el vehiculo que salio del parqueadero
	 * 
	 * @param vehiculoSalida, el nuevo vehiculo de salida del parqueadero
	 */
	public void setVehiculoSalida(VehiculoEntity vehiculoSalida) {
		this.vehiculoSalida = vehiculoSalida;
	}

	/**
	 * método encargado de obtener la fecha de salida del vehiculo del parqueadero
	 * 
	 * @return fechaSalida, la fecha de salida del vehiculo
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_SALIDA", nullable = false)
	public Date getFechaSalida() {
		return fechaSalida;
	}

	/**
	 * métodoen cargado de setear la fecha de salida del vehiculo del parqueadero
	 * 
	 * @param fechaSalida, la nueva fecha de salida del parqueadero
	 */
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	/**
	 * método encargado de obtener el preocio pagado por le vehiculo
	 * 
	 * @return precioPagado, el precio pagado por el vehiculo
	 */
	@Column(name = "PRECIO_PAGADO", nullable = false)
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

}
