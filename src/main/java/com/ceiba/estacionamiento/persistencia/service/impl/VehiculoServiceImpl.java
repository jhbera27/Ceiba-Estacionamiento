package com.ceiba.estacionamiento.persistencia.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

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

	private final VehiculoRepository vehiculoRepository;

	public VehiculoServiceImpl(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}

	/**
	 * M�todo encargado consultar todos los vehiculos en el parqueadero
	 * 
	 * @return la lista de vehiculos que se encuentran en el parqueadero
	 */
	@Override
	public List<Vehiculo> obtenerVehiculos() {
		List<VehiculoEntity> listaVehiculosEntity = vehiculoRepository.obtenerVehiculos();
		List<Vehiculo> listaVehiculos = new ArrayList<>();
		for (VehiculoEntity vehiculoEntity : listaVehiculosEntity) {
			listaVehiculos.add(VehiculoBuilder.convertirADominio(vehiculoEntity));
		}
		return listaVehiculos;
	}

	/**
	 * M�todo encargado de guardar un vehiculo en el parqueadero, primero se
	 * verifica si el vehiculo puede registrarse,si es posible se busca si ya existe
	 * un vehiculo con esa placa, si existe se actualiza en caso contrario se crea
	 * 
	 * @param vehiculo, el vehiculo a guardar
	 */
	@Override
	public void agregar(Vehiculo vehiculo) {
		validarCreacionVehiculo(vehiculo);
		verificarPlaca(vehiculo.getPlaca());
		Vehiculo vehiculoEncontrado = buscarVehiculoPorPlaca(vehiculo.getPlaca());
		if (vehiculoEncontrado != null) {
			vehiculoEncontrado.setEstaParqueado(Boolean.TRUE);
			vehiculoEncontrado.setFechaIngreso(new Date());
			vehiculoRepository.save(VehiculoBuilder.convertirAEntity(vehiculoEncontrado));
		} else {
			vehiculoRepository.save(VehiculoBuilder.convertirAEntity(vehiculo));
		}
	}

	/**
	 * M�todo encargado de consultar un vehiculo por su placa
	 * 
	 * @param placa, la placa del vehiculo a registrar
	 * @return Vehiculo, el vehiculo asociado a la placa
	 */
	@Override
	public Vehiculo buscarVehiculoPorPlaca(String placa) {
		VehiculoEntity vehiculo;
		try {
			vehiculo = vehiculoRepository.buscarVehiculoPorPlaca(placa);
		} catch (NoResultException e) {
			vehiculo = null;
		}
		return vehiculo != null ? VehiculoBuilder.convertirADominio(vehiculo) : null;
	}

	/**
	 * M�todo encargado de verificar si se puede registrar el vehiculo dado la regla
	 * de negocio que solo se permiten 20 carro y 10 motos simultaneamente
	 * 
	 * @param nuevoVehiculo, el vehiculo a crear
	 */
	public void validarCreacionVehiculo(Vehiculo nuevoVehiculo) {
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

	/**
	 * M�todo encargado de verificar si la placa del vehiculo a ingresar empieza por
	 * la tecla A, si es asi verifica el dia de la semana
	 * 
	 * @param placa, la placa del vehiculo a registrar
	 * @return true, si puede ingresar el vehiculo, false en caso contrario
	 */
	public void verificarPlaca(String placa) {
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