package com.ceiba.estacinamiento.dominio;

import java.io.Serializable;

public class CalcularPrecio implements Serializable {
	/**
	 * atributo para la serializacion
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * atributo que determina el vehiculo que salio del parqueadero
	 */
	private Vehiculo vehiculoSalida;
	/**
	 * atributo que determina el valor de la hora del vehiculo
	 */
	private int valorHoraVehiculo;
	/**
	 * atributo que determina el valor del del vehiculo
	 */
	private int valorDiaVehiculo;

	/**
	 * metodo contructor vacio
	 */
	public CalcularPrecio() {

	}

	/**
	 * Metodo constructor de la calse
	 * 
	 * @param vehiculoSalida, el vehiculo al cual se le va a registrar la salida
	 * @param valorHoraVehiculo, el valor por hora del vehiculo
	 * @param valorDiaVehiculo, el valor por dia del vehiculo
	 */
	public CalcularPrecio(Vehiculo vehiculoSalida, int valorHoraVehiculo, int valorDiaVehiculo) {
		super();
		this.vehiculoSalida = vehiculoSalida;
		this.valorHoraVehiculo = valorHoraVehiculo;
		this.valorDiaVehiculo = valorDiaVehiculo;
	}

	/**
	 * método encargado de obtener el vehiculo que va a salir del parqueadero
	 * 
	 * @return vehiculoSalida, el vehiculo que va a salir del parqueadero
	 */
	public Vehiculo getVehiculoSalida() {
		return vehiculoSalida;
	}

	/**
	 * método en cargado de setear el vehiculo que va a salir del parqueadero
	 * 
	 * @param vehiculoSalida, el nuevo vehiculo que va a salir del parqueadero
	 */
	public void setVehiculoSalida(Vehiculo vehiculoSalida) {
		this.vehiculoSalida = vehiculoSalida;
	}

	/**
	 * Método encargado de obtener el valor de la hora del vehiculo en el
	 * parqueadero
	 * 
	 * @return valorHoraVehiculo, el valor de la hora del vehiculo en el paruqdaro
	 */
	public int getValorHoraVehiculo() {
		return valorHoraVehiculo;
	}

	/**
	 * método para setear el valor de la hora del vehiculo en el parqueadero
	 * 
	 * @param valorHoraVehiculo, el nuevo valor de la hora
	 */
	public void setValorHoraVehiculo(int valorHoraVehiculo) {
		this.valorHoraVehiculo = valorHoraVehiculo;
	}

	/**
	 * Método encargado de obtener el valor del dia del vehiculo en el parqueadero
	 * 
	 * @return valorDiaVehiculo, el valor del dia del vehiculo en el paruqdaro
	 */
	public int getValorDiaVehiculo() {
		return valorDiaVehiculo;
	}
    
	/**
	 * método para setear el valor del dia del vehiculo en el parqueadero
	 * 
	 * @param valorDiaVehiculo, el nuevo valor del dia
	 */
	public void setValorDiaVehiculo(int valorDiaVehiculo) {
		this.valorDiaVehiculo = valorDiaVehiculo;
	}

}
