package com.ceiba.estacionamiento.dominio.excepcion;

/**
 * clase que permite controlar las excepciones en tiepo de ejecucion
 * 
 * @author jhon.bedoya
 *
 */
public class VehiculoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Método constructor de la clase
	 * 
	 * @param message, el mensaje de retroalimentacion para el usuario
	 */
	public VehiculoException(String message) {
		super(message);
	}
}
