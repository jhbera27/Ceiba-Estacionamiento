package com.ceiba.estacionamiento.persistencia.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.establecimiento.enums.TipoVehiculoEnum;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.dominio.excepcion.VehiculoException;
import com.ceiba.estacionamiento.persistencia.builder.VehiculoBuilder;
import com.ceiba.estacionamiento.persistencia.entity.VehiculoEntity;
import com.ceiba.estacionamiento.persistencia.repositorio.VehiculoRepository;
import com.ceiba.estacionamiento.persistencia.service.VehiculoService;

@Service
@Transactional
public class VehiculoServiceImpl implements VehiculoService {

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
	 * atributo que representa la letra no permitida para las placas en el
	 * parqueadero
	 */
	private static final String LETRA_PLACA = "A";
	/**
	 * atributo que representa el numero maximo de motos en el parqueadero
	 */
	public static final int NUMERO_MAXIMO_MOTOS = 10;
	/**
	 * atributo que representa el numero maximo de carros en el parqueadero
	 */
	public static final int NUMERO_MAXIMO_CARROS = 20;

	@Autowired
	private final VehiculoRepository vehiculoRepository;

	public VehiculoServiceImpl(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}


	@Override
	public List<Vehiculo> obtenerVehiculos() {
		List<VehiculoEntity> listaVehiculosEntity = vehiculoRepository.obtenerVehiculos();
		List<Vehiculo> listaVehiculos = new ArrayList<>();
		for (VehiculoEntity vehiculoEntity : listaVehiculosEntity) {
			listaVehiculos.add(VehiculoBuilder.convertirADominio(vehiculoEntity));
		}
		return listaVehiculos;
	}

	@Override
	public void agregarVehiculoParqueadero(Vehiculo vehiculo) {
		validarCupoParqueadero(vehiculo);
		verificarPlacaPermitida(vehiculo.getPlaca());
		Vehiculo vehiculoEncontrado = buscarVehiculoPorPlaca(vehiculo.getPlaca());
		if (vehiculoEncontrado != null) {
			vehiculoEncontrado.setEstaParqueado(Boolean.TRUE);
			vehiculoEncontrado.setFechaIngreso(new Date());
			vehiculoRepository.save(VehiculoBuilder.convertirAEntity(vehiculoEncontrado));
		} else {
			vehiculoRepository.save(VehiculoBuilder.convertirAEntity(vehiculo));
		}
	}

	@Override
	public Vehiculo buscarVehiculoPorPlaca(String placa) {
		VehiculoEntity vehiculo = null;
		vehiculo = vehiculoRepository.buscarVehiculoPorPlaca(placa);
		return vehiculo != null ? VehiculoBuilder.convertirADominio(vehiculo) : null;
	}


	public void validarCupoParqueadero(Vehiculo nuevoVehiculo) {
		List<Vehiculo> listaVehiculos = obtenerVehiculos();
		List<Vehiculo> listaVehiculosPorTipo = listaVehiculos.stream()
				.filter(vehiculo -> vehiculo.getTipo().equals(nuevoVehiculo.getTipo())).collect(Collectors.toList());

		if (TipoVehiculoEnum.MOTO.name().equals(nuevoVehiculo.getTipo())
				&& NUMERO_MAXIMO_MOTOS <= listaVehiculosPorTipo.size()) {
			throw new VehiculoException(SIN_CUPO);
		}
		if (TipoVehiculoEnum.CARRO.name().equals(nuevoVehiculo.getTipo())
				&& NUMERO_MAXIMO_CARROS <= listaVehiculosPorTipo.size()) {
			throw new VehiculoException(SIN_CUPO);
		}
	}

	public void verificarPlacaPermitida(String placa) {
		String placaM = placa.toUpperCase();
		if (!placa.isEmpty() && LETRA_PLACA.equals(Character.toString(placaM.charAt(0)))) {
			LocalDate today = LocalDate.now();
			DayOfWeek day = today.getDayOfWeek();
			Set<DayOfWeek> diasNoPermitidos = EnumSet.of(DayOfWeek.SUNDAY, DayOfWeek.MONDAY);
			if (!diasNoPermitidos.contains(day)) {
				throw new VehiculoException(VEHICULO_NO_AUTORIZADO);
			}
		}
	}

}
