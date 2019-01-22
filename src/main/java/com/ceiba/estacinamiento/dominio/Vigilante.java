package com.ceiba.estacinamiento.dominio;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import com.ceiba.establecimiento.enums.TipoVehiculoEnum;
import com.ceiba.estacionamiento.dominio.excepcion.VehiculoException;

public class Vigilante {
	
	/**
	 * atributo que contiene el mensaje de respuesta para los vehiculos no
	 * autorizados
	 */
	public static final String VEHICULO_NO_AUTORIZADO = "El vehiculo no esta autorizado para ingresar";
	/**
	 * atributo que contiene el mensaje de respuesta cuando no hay cupo en el
	 * parqueadero
	 */
	public static final String SIN_CUPO = "No hay cupos en el estacionamiento";
	/**
	 * atributo que representa el numero maximo de motos en el parqueadero
	 */
	public static final int NUMERO_MAXIMO_MOTOS = 10;
	/**
	 * atributo que representa el numero maximo de carros en el parqueadero
	 */
	public static final int NUMERO_MAXIMO_CARROS = 20;
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
	 * atributo que representa el numero de horas por dia en el parqueadero
	 */
	private static final int HORAS_POR_DIA = 9;
	/**
	 * atributo que representa el cilindraje base de una moto
	 */
	private static final int CILINDRAJE = 500;
	/**
	 * atributo que representa el valor adicional que debe pagar una moto si su
	 * cilindraje es mayor a 500
	 */
	private static final int VALOR_ADICIONAL_MOTO = 2000;
	

	/**
	 * Métodoencargado de verificar si la placa del vehiculo a ingresar empieza por
	 * la tecla A, si es asi verifica el dia de la semana
	 * 
	 * @param placa, la placa del vehiculo a registrar
	 * @return true, si puede ingresar el vehiculo, false en caso contrario
	 */
	public static void verificarPlaca(String placa) {
		String placaM = placa.toUpperCase();
		if (!placa.isEmpty() && LETRA_PLACA.equals(Character.toString(placaM.charAt(0)))) {
			LocalDate today = LocalDate.now();
			DayOfWeek day = today.getDayOfWeek();
			Set<DayOfWeek> diasNoPermitidos = EnumSet.of(DayOfWeek.SUNDAY, DayOfWeek.MONDAY);
			if(!diasNoPermitidos.contains(day)) {
				throw new VehiculoException(VEHICULO_NO_AUTORIZADO);
			}
		}
	}
	
	/**
	 * Método encargado de calcular el precio total a pagar teniendo en cuenta las
	 * reglas de negocio de tipo de vehiculo, numero de horas ,valor hora y dia
	 * 
	 * @param vehiculoSalida, el vehiculo que va a salir del parqueadero
	 * @param valorHoraVehiculo, valor de una hora en el parqueadero
	 * @param valorDiaVehiculo, valor de un dia en el parqueadero
	 * @return el precio total a pagar para el vehiculo
	 */
	public static BigDecimal calcularPrecioAPagar(Vehiculo vehiculoSalida, int valorHoraVehiculo, int valorDiaVehiculo) {
		Long horasDiferencia = diferenciaHoras(vehiculoSalida.getFechaIngreso());
		BigDecimal pago = new BigDecimal(0);
		if (HORAS_POR_DIA >= horasDiferencia) {
			pago = calcularPrecioPorHoras(horasDiferencia, valorHoraVehiculo);
		} else if (horasDiferencia <= 24) {
			pago = pago.add(new BigDecimal(valorDiaVehiculo));
		} else {
			BigDecimal numDias = new BigDecimal(horasDiferencia / 24);
			pago = numDias.multiply(new BigDecimal(valorDiaVehiculo));
			BigDecimal horas = new BigDecimal(horasDiferencia % 24);
			if (HORAS_POR_DIA >= horas.intValue()) {
				pago = pago.add(horas.multiply(new BigDecimal(valorHoraVehiculo)));
			} else {
				BigDecimal numDiasDos = new BigDecimal(horas.intValue() / HORAS_POR_DIA);
				BigDecimal horasDos = new BigDecimal(horas.intValue() % HORAS_POR_DIA);
				pago = pago.add(numDiasDos.multiply(new BigDecimal(valorDiaVehiculo)));
				pago = pago.add(horasDos.multiply(new BigDecimal(valorHoraVehiculo)));
			}
		}
		if (TipoVehiculoEnum.MOTO.name().equals(vehiculoSalida.getTipo())
				&& CILINDRAJE < vehiculoSalida.getCilindraje()) {
			pago = pago.add(new BigDecimal(VALOR_ADICIONAL_MOTO));
		}
		return pago;
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
