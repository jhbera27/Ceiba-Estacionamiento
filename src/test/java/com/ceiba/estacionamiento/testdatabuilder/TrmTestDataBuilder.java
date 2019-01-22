package com.ceiba.estacionamiento.testdatabuilder;

import java.util.Date;
import com.ceiba.estacinamiento.dominio.Trm;

public class TrmTestDataBuilder {
	/**
	 * identificador por defecto para el TrmTestDatBuilder
	 */
	private static final Long ID = 1L;
	/**
	 * unit por defecto para el TrmTestDatBuilder
	 */
	private static final String UNIT = "COP";
	/**
	 * valor por defecto para el TrmTestDatBuilder
	 */
	private static final float VALOR = 3452f;
	
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
	
	public TrmTestDataBuilder() {
		this.id = ID;
		this.fechaValidaDesde = new Date();
		this.fechaValidaHasta = new Date();
		this.unit = UNIT;
		this.valor = VALOR;
	}
	
	/**
	 * Método encargado de construir el objeto Trm del dominio
	 * 
	 * @return Trm, el trm creado
	 */
	public Trm build() {
		return new Trm(this.id, this.fechaValidaDesde, this.fechaValidaHasta, this.unit, this.valor);
	}


}
