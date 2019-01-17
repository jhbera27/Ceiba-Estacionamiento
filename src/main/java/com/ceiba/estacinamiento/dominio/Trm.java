package com.ceiba.estacinamiento.dominio;

import java.io.Serializable;
import java.util.Date;
/**
 * Clase que representa la informacion de  un trm
 * @author jhon.bedoya
 *
 */
public class Trm implements Serializable {

	/**
	 * atributo para la serializacion
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Atributo que determina el identificador del trm
	 */
	private Long id;
	/**
	 * Atributo que determina la fecha de validez del trm
	 */
	private Date fechaValidaDesde;
	/**
	 * Atributo que determina la fecha en que termina la validez del trm
	 */
	private Date fechaValidaHasta;
	/**
	 * Atributo que determina la moneda del trm
	 */
	private String unit;
	/**
	 * Atributo que determina el valor del trm
	 */
	private Float valor;

	/**
	 * constructor vacio de la calse
	 */
	public Trm() {

	}

	/**
	 * m�todo controctor de la clase
	 * 
	 * @param id, el identificador del trm
	 * @param fechaValidaDesde, la fecha de inicio de vigencia del trm
	 * @param fechaValidaHasta, la fecha de fin de la vigencia del trm
	 * @param unit, la modena del trm
	 * @param valor, el valor del trm
	 */
	public Trm(Long id, Date fechaValidaDesde, Date fechaValidaHasta, String unit, Float valor) {
		super();
		this.id = id;
		this.fechaValidaDesde = fechaValidaDesde;
		this.fechaValidaHasta = fechaValidaHasta;
		this.unit = unit;
		this.valor = valor;
	}

	/**
	 * m�todo encargado de obtener el identificador del trm
	 * 
	 * @return id, el identificador del trm
	 */
	public Long getId() {
		return id;
	}
    /**
     * m�todo encargado de setear el identificador del trm
     * @param id, el nuevo identificador del trm
     */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * m�todo encargado de obtener la vigencia del trm
	 * 
	 * @return fechaValidaDesde, la vigencia del trm
	 */
	public Date getFechaValidaDesde() {
		return fechaValidaDesde;
	}
    /**
     * m�todo encargado de setear la vigencia del trm
     * @param fechaValidaDesde, la nueva vigencia del trm
     */
	public void setFechaValidaDesde(Date fechaValidaDesde) {
		this.fechaValidaDesde = fechaValidaDesde;
	}
	/**
	 * m�todo encargado de obtener el fin de la vigencia del trm
	 * 
	 * @return fechaValidaHasta, el fin de la vigencia del trm
	 */
	public Date getFechaValidaHasta() {
		return fechaValidaHasta;
	}
	  /**
     * m�todo encargado de setear el fin de la vigencia del trm
     * @param fechaValidaHasta, el nuevo fin de la vigencia del trm
     */
	public void setFechaValidaHasta(Date fechaValidaHasta) {
		this.fechaValidaHasta = fechaValidaHasta;
	}
	/**
	 * m�todo encargado de obtener la moneda del trm
	 * 
	 * @return unit, la moneda del trm
	 */
	public String getUnit() {
		return unit;
	}
	  /**
     * m�todo encargado de setear la moneda del trm
     * @param unit, la moneda del trm
     */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * m�todo encargado de obtenerel valor del trm
	 * 
	 * @return valor, el valor del trm
	 */
	public Float getValor() {
		return valor;
	}
	  /**
     * m�todo encargado de setear el valor del trm
     * @param valor, el nuevo valor del trm
     */
	public void setValor(Float valor) {
		this.valor = valor;
	}
}
