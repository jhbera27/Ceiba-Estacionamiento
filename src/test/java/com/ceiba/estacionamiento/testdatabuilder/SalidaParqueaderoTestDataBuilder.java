package com.ceiba.estacionamiento.testdatabuilder;

import java.math.BigDecimal;
import java.util.Date;

import com.ceiba.estacinamiento.dominio.SalidaParqueadero;
import com.ceiba.estacinamiento.dominio.Vehiculo;

/**
 * clase para la creacion de objetos tipo SalidaParqueadero para las pruebas
 * unitarias y de integracion
 * 
 * @author jhon.bedoya
 *
 */
public class SalidaParqueaderoTestDataBuilder {
	/**
	 * precio pagado por defecto del objeto SalidaParqueaderoTestDataBuilder
	 */
	private static final BigDecimal PRECIO_PAGADO = new BigDecimal(500000);
	/**
	 * vehiculo salida por defecto del objeto SalidaParqueaderoTestDataBuilder
	 */
	private static final Vehiculo VEHICULO = new VehiculoTestDataBuilder().build();
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
	 * metodo contructor para la clase
	 */
	public SalidaParqueaderoTestDataBuilder() {
		this.precioPagado = PRECIO_PAGADO;
		this.vehiculoSalida = VEHICULO;
	}

	/**
	 * Método encargado de asiganarle un nuevo valor al atributo id del objeto
	 * SalidaParqueaderoTestDataBuilder
	 * 
	 * @param id, el identificador de la salida del vehiculo del parqueadero
	 * @return SalidaParqueaderoTestDataBuilder, la salida del parqueadero data
	 *         builder con el atributo seteado
	 */
	public SalidaParqueaderoTestDataBuilder conId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * Método encargado de asiganarle un nuevo valor al atributo vehiculoSalida del
	 * objeto SalidaParqueaderoTestDataBuilder
	 * 
	 * @param vehiculo, el vehiculo que sale del parqueadero
	 * @return SalidaParqueaderoTestDataBuilder, la salida del parqueadero data
	 *         builder con el nuevo vehiculo seteado
	 */
	public SalidaParqueaderoTestDataBuilder conVehiculoSalida(Vehiculo vehiculo) {
		this.vehiculoSalida = vehiculo;
		return this;
	}
	
	/**
	 * Método encargado de asiganarle un nuevo valor al atributo precioPagado del objeto
	 * SalidaParqueaderoTestDataBuilder
	 * 
	 * @param precioPagado, el precio pagado por el vehiculo 
	 * @return SalidaParqueaderoTestDataBuilder, la salida del parqueadero data
	 *         builder con el atributo seteado
	 */
	public SalidaParqueaderoTestDataBuilder conPrecioPagado(BigDecimal precioPagado) {
		this.precioPagado = precioPagado;
		return this;
	}
	
	/**
	 * Método encargado de asiganarle un nuevo valor al atributo fechaSalida del objeto
	 * SalidaParqueaderoTestDataBuilder
	 * 
	 * @param fechaSalida, la fecha de salida del vehiculo del parqueadero
	 * @return SalidaParqueaderoTestDataBuilder, la salida del parqueadero data
	 *         builder con el atributo seteado
	 */
	public SalidaParqueaderoTestDataBuilder conFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
		return this;
	}
	
	/**
	 * Método encargado de construir el objeto SalidaParqueadero del dominio
	 * 
	 * @return SalidaParqueadero, el objeto SalidaParqueadero creado
	 */
	public SalidaParqueadero build() {
		return new SalidaParqueadero(id, vehiculoSalida, precioPagado, fechaSalida);
	}

}
