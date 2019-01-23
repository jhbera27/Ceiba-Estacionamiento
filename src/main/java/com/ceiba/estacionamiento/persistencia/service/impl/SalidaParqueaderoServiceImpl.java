package com.ceiba.estacionamiento.persistencia.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.establecimiento.enums.TipoVehiculoEnum;
import com.ceiba.estacinamiento.dominio.SalidaParqueadero;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.persistencia.builder.SalidaParqueaderoBuilder;
import com.ceiba.estacionamiento.persistencia.builder.VehiculoBuilder;
import com.ceiba.estacionamiento.persistencia.entity.SalidaParqueaderoEntity;
import com.ceiba.estacionamiento.persistencia.repositorio.SalidaParqueaderoRepository;
import com.ceiba.estacionamiento.persistencia.repositorio.VehiculoRepository;
import com.ceiba.estacionamiento.persistencia.service.SalidaParqueaderoService;

@Service
@Transactional
public class SalidaParqueaderoServiceImpl implements SalidaParqueaderoService {
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
	 * atributo que representa el valor de una hora para una moto en el parqueadero
	 */
	public static final int VALOR_HORA_MOTO = 500;
	/**
	 * atributo que representa el valor de una hora para un carro en el parqueadero
	 */
	public static final int VALOR_HORA_CARRO = 1000;
	/**
	 * atributo que representa el valor de un dia para un carro en el parqueadero
	 */
	public static final int VALOR_DIA_CARRO = 8000;
	/**
	 * atributo que representa el valor de un dia para un carro en el parqueadero
	 */
	public static final int VALOR_DIA_MOTO = 4000;

	@Autowired
	private final SalidaParqueaderoRepository salidaParqueaderoRepository;
	
	@Autowired
	private final VehiculoRepository vehiculoRepository;

	public SalidaParqueaderoServiceImpl(SalidaParqueaderoRepository salidaParqueaderoRepository,
			VehiculoRepository vehiculoRepository) {
		this.salidaParqueaderoRepository = salidaParqueaderoRepository;
		this.vehiculoRepository = vehiculoRepository;
	}

	@Override
	public void agregar(SalidaParqueadero salidaParqueadero) {
		Vehiculo vehiculoSalida = salidaParqueadero.getVehiculoSalida();
		vehiculoSalida.setEstaParqueado(Boolean.FALSE);
		if (TipoVehiculoEnum.MOTO.name().equals(vehiculoSalida.getTipo())) {
			salidaParqueadero.setPrecioPagado(calcularPrecioAPagar(vehiculoSalida, VALOR_HORA_MOTO, VALOR_DIA_MOTO));
		} else {
			salidaParqueadero.setPrecioPagado(calcularPrecioAPagar(vehiculoSalida, VALOR_HORA_CARRO, VALOR_DIA_CARRO));
		}
		salidaParqueaderoRepository.save(SalidaParqueaderoBuilder.convertirAEntity(salidaParqueadero));
		vehiculoRepository.save(VehiculoBuilder.convertirAEntity(vehiculoSalida));
	}

	@Override
	public List<SalidaParqueadero> obtenerSalidaVehiculosParqueadero() {
		List<SalidaParqueaderoEntity> listaSalidaVehiculoParqueaderoEntity = salidaParqueaderoRepository
				.obtenerSalidaVehiculosParqueadero();
		List<SalidaParqueadero> listaSalidaParqueadero = new ArrayList<>();
		for (SalidaParqueaderoEntity salidaParqueaderoEntity : listaSalidaVehiculoParqueaderoEntity) {
			listaSalidaParqueadero.add(SalidaParqueaderoBuilder.convertirADominio(salidaParqueaderoEntity));
		}
		return listaSalidaParqueadero;
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
	@Override
	public BigDecimal calcularPrecioAPagar(Vehiculo vehiculoSalida, int valorHoraVehiculo, int valorDiaVehiculo) {
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
	public BigDecimal calcularPrecioPorHoras(Long horas, int precio) {
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
	public Long diferenciaHoras(Date fecha) {
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
