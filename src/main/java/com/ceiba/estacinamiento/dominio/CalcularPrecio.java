package com.ceiba.estacinamiento.dominio;

import java.io.Serializable;

public class CalcularPrecio implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Vehiculo vehiculoSalida;

	private int valorHoraVehiculo;

	private int valorDiaVehiculo;


	public CalcularPrecio() {

	}

	public CalcularPrecio(Vehiculo vehiculoSalida, int valorHoraVehiculo, int valorDiaVehiculo) {
		super();
		this.vehiculoSalida = vehiculoSalida;
		this.valorHoraVehiculo = valorHoraVehiculo;
		this.valorDiaVehiculo = valorDiaVehiculo;
	}

	public Vehiculo getVehiculoSalida() {
		return vehiculoSalida;
	}

	public void setVehiculoSalida(Vehiculo vehiculoSalida) {
		this.vehiculoSalida = vehiculoSalida;
	}

	public int getValorHoraVehiculo() {
		return valorHoraVehiculo;
	}

	public void setValorHoraVehiculo(int valorHoraVehiculo) {
		this.valorHoraVehiculo = valorHoraVehiculo;
	}

	public int getValorDiaVehiculo() {
		return valorDiaVehiculo;
	}
    
	public void setValorDiaVehiculo(int valorDiaVehiculo) {
		this.valorDiaVehiculo = valorDiaVehiculo;
	}

}
