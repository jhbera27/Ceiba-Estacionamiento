package com.ceiba.estacionamiento.persistencia.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ceiba.establecimiento.enums.TipoVehiculoEnum;

/**
 * Clase que representa el entity para un vehiculo
 * 
 * @author jhon.bedoya
 *
 */
@Entity
@Table(name = "VEHICULO")
public class VehiculoEntity implements Serializable {

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
	private TipoVehiculoEnum tipo;
	/**
	 * atributo que determina el ciclindraje del vehiculo
	 */
	private Integer cilindraje;
	/**
	 * atributo que determina si el vehiculo se encuentra parqueado
	 */
	private Boolean estaParqueado;

	/**
	 * método encargado de obtener el identificador de un vehiculo
	 * 
	 * @return id, el identificador del vehiculo
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
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
	@Column(name = "PLACA", length = 6, nullable = false)
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_INGRESO", nullable = false)
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
	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO_VEHICULO", length = 5, nullable = false)
	public TipoVehiculoEnum getTipo() {
		return tipo;
	}

	/**
	 * método encargado de setear el tipo de vehiculo
	 * 
	 * @param tipo, el nuevo tipo de vehiculo
	 */
	public void setTipo(TipoVehiculoEnum tipo) {
		this.tipo = tipo;
	}

	/**
	 * método encargado de obtener el cilindraje del vehiculo
	 * 
	 * @return cilindraje, el cilindraje del vehiculo
	 */
	@Column(name = "CILINDRAJE", length = 4)
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
	@Column(name = "ESTA_PARQUEADO", nullable = false)
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
