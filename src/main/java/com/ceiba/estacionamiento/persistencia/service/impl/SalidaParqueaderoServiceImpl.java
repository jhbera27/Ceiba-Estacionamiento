package com.ceiba.estacionamiento.persistencia.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.ceiba.establecimiento.enums.TipoVehiculoEnum;
import com.ceiba.estacinamiento.dominio.SalidaParqueadero;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacinamiento.dominio.Vigilante;
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

	private final SalidaParqueaderoRepository salidaParqueaderoRepository;
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

	@Override
	public BigDecimal calcularPrecioAPagar(Vehiculo vehiculoSalida, int valorHoraVehiculo, int valorDiaVehiculo) {
		return Vigilante.calcularPrecioAPagar(vehiculoSalida, valorHoraVehiculo, valorDiaVehiculo);
	}

}
