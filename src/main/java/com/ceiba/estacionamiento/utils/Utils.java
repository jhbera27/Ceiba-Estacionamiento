package com.ceiba.estacionamiento.utils;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Clase que determina metos utiles para la aplicacion
 * 
 * @author jhon.bedoya
 *
 */
public class Utils {

	/**
	 * atributo que representa la letra no permitida para las placas en el
	 * parqueadero
	 */
	private static final String LETRA_PLACA = "A";
	/**
	 * atributo que representa los milisegundos en una hora
	 */
	private static final int HORA_EN_MILISEGUNDOS = 3600000;

	/**
	 * método contructor de la clase Utils
	 */
	private Utils() {

	}

	/**
	 * Métodoencargado de verificar si la placa del vehiculo a ingresar empieza por
	 * la tecla A, si es asi verifica el dia de la semana
	 * 
	 * @param placa, la placa del vehiculo a registrar
	 * @return true, si puede ingresar el vehiculo, false en caso contrario
	 */
	public static Boolean verificarPlaca(String placa) {
		String placaM = placa.toUpperCase();
		if (!placa.isEmpty() && LETRA_PLACA.equals(Character.toString(placaM.charAt(0)))) {
			LocalDate today = LocalDate.now();
			DayOfWeek day = today.getDayOfWeek();
			Set<DayOfWeek> diasNoPermitidos = EnumSet.of(DayOfWeek.SUNDAY, DayOfWeek.MONDAY);
			return !diasNoPermitidos.contains(day);

		}
		return Boolean.TRUE;
	}

	/**
	 * Método encargado de calcular el precio de las horas en el parqueadero
	 * 
	 * @param horas, el numero de horas que estuvo el vehiculo en el paruqeadero
	 * @param precio, el precio por cado hora
	 * @return el precio a pagar por las horas
	 */
	public static BigDecimal calcularPrecioPorHoras(Long horas, int precio) {
		BigDecimal horasBigdecimal = new BigDecimal(horas);
		return horasBigdecimal.multiply(new BigDecimal(precio));
	}

	/**
	 * Método encargado de calcular la diferencia de horas entre la fecha ingreso y
	 * la fecha de salida
	 * 
	 * @param fecha, la fecha de ingreso al parqueadero
	 * @return diferencia en horas entre la fecha de ingreso y salida
	 */
	public static Long diferenciaHoras(Date fecha) {
		Long diferenciaHoras = new Date().getTime() - fecha.getTime();
		if (HORA_EN_MILISEGUNDOS > diferenciaHoras) {
			return TimeUnit.MILLISECONDS.toHours(HORA_EN_MILISEGUNDOS);
		} else {
			diferenciaHoras = diferenciaHoras / HORA_EN_MILISEGUNDOS;
			Long fraccion = diferenciaHoras % HORA_EN_MILISEGUNDOS;

			return fraccion > 0 ? diferenciaHoras + 1 : diferenciaHoras;
		}
	}
}
