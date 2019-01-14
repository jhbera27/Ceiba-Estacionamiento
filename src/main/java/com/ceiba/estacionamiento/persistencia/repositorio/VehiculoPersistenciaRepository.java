package com.ceiba.estacionamiento.persistencia.repositorio;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ceiba.establecimiento.enums.TipoVehiculoEnum;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.dominio.excepcion.VehiculoException;
import com.ceiba.estacionamiento.dominio.repositorio.VehiculoRepository;
import com.ceiba.estacionamiento.persistencia.builder.VehiculoBuilder;
import com.ceiba.estacionamiento.persistencia.entity.VehiculoEntity;

/**
 * clase encargada de realizar toda la logica de negocio para la creacion de
 * vehiculos
 * 
 * @author jhon.bedoya
 *
 */
@Service
@Transactional
public class VehiculoPersistenciaRepository implements VehiculoRepository {

	/**
	 * atributo que representa la letra no permitida para las placas en el
	 * parqueadero
	 */
	private static final String LETRA_PLACA = "A";
	/**
	 * atributo que contiene el mensaje de respuesta para los vehiculos no
	 * autorizados
	 */
	public static final String VEHICULO_NO_AUTORIZADO = "El vehiculo no esta autorizado para ingresar";
	/**
	 * atributo que contiene el mensaje de respuesta cuando no hay cupo en el
	 * parqueadero
	 */
	public static final String SIN_CUPO = "No hay cupos en el estacionamientor";
	/**
	 * atributo que representa el numero maximo de motos en el parqueadero
	 */
	private static final int NUMERO_MAXIMO_MOTOS = 10;
	/**
	 * atributo que representa el numero maximo de carros en el parqueadero
	 */
	private static final int NUMERO_MAXIMO_CARROS = 20;

	/**
	 * atributo que representa el contexto de persistencia para el repository
	 */
	private EntityManager entityManager;

	public VehiculoPersistenciaRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Método encargado consultar todos los vehiculos en el parqueadero
	 * 
	 * @return la lista de vehiculos que se encuentran en el parqueadero
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Vehiculo> obtenerVehiculos() {
		List<VehiculoEntity> listaVehiculosEntity = entityManager
				.createQuery("SELECT v FROM VehiculoEntity v WHERE v.estaParqueado = 1 ORDER BY v.placa")
				.getResultList();

		List<Vehiculo> listaVehiculos = new ArrayList<>();
		for (VehiculoEntity vehiculoEntity : listaVehiculosEntity) {
			listaVehiculos.add(VehiculoBuilder.convertirADominio(vehiculoEntity));
		}
		return listaVehiculos;
	}

	/**
	 * Método encargado de guardar un vehiculo en el parqueadero, primero se
	 * verifica si el vehiculo puede registrarse,si es posible se busca si ya existe
	 * un vehiculo con esa placa, si existe se actualiza en caso contrario se crea
	 * 
	 * @param vehiculo, el vehiculo a guardar
	 */
	@Override
	public void agregar(Vehiculo vehiculo) {
		if (!validarCreacionVehiculo(vehiculo)) {
			throw new VehiculoException(SIN_CUPO);
		}
		if (!verificarPlaca(vehiculo.getPlaca())) {
			throw new VehiculoException(VEHICULO_NO_AUTORIZADO);
		}
		Vehiculo vehiculoEncontrado = buscarVehiculoPorPlaca(vehiculo.getPlaca());
		if (vehiculoEncontrado != null) {
			vehiculoEncontrado.setEstaParqueado(Boolean.TRUE);
			vehiculoEncontrado.setFechaIngreso(new Date());
			entityManager.merge(VehiculoBuilder.convertirAEntity(vehiculoEncontrado));
		} else {
			entityManager.persist(VehiculoBuilder.convertirAEntity(vehiculo));
		}
	}

	/**
	 * Método encargado de consultar un vehiculo por su placa
	 * 
	 * @param placa, la placa del vehiculo a registrar
	 * @return Vehiculo, el vehiculo asociado a la placa
	 */
	@Override
	public Vehiculo buscarVehiculoPorPlaca(String placa) {
		VehiculoEntity vehiculoEntity;
		try {
			vehiculoEntity = (VehiculoEntity) entityManager
					.createQuery("SELECT v FROM VehiculoEntity v WHERE v.placa =:placa").setParameter("placa", placa)
					.getSingleResult();
		} catch (NoResultException e) {
			vehiculoEntity = null;
		}
		return vehiculoEntity != null ? VehiculoBuilder.convertirADominio(vehiculoEntity) : null;
	}

	/**
	 * Método encargado de verificar si se puede registrar erl vehiculo dado la
	 * regla de negocio que solo se permiten 20 carro y 10 motos siimultaneamente
	 * 
	 * @param nuevoVehiculo, el vehiculo a crear
	 * @return true, si se puede registrar el vehiculo, false en caso contrario
	 */
	public Boolean validarCreacionVehiculo(Vehiculo nuevoVehiculo) {

		List<Vehiculo> listaVehiculos = obtenerVehiculos();
		List<Vehiculo> listaVehiculosPorTipo = listaVehiculos.stream()
				.filter(vehiculo -> vehiculo.getTipo().equals(nuevoVehiculo.getTipo())).collect(Collectors.toList());

		if (TipoVehiculoEnum.MOTO.equals(nuevoVehiculo.getTipo())) {
			return listaVehiculosPorTipo.size() < NUMERO_MAXIMO_MOTOS ? Boolean.TRUE : Boolean.FALSE;
		} else {
			return listaVehiculosPorTipo.size() < NUMERO_MAXIMO_CARROS ? Boolean.TRUE : Boolean.FALSE;
		}

	}

	/**
	 * Métodoencargado de verificar si la placa del vehiculo a ingresar empieza por
	 * la tecla A, si es asi verifica el dia de la semana
	 * 
	 * @param placa, la placa del vehiculo a registrar
	 * @return true, si puede ingresar el vehiculo, false en caso contrario
	 */
	public Boolean verificarPlaca(String placa) {
		String placaM = placa.toUpperCase();
		if (!placa.isEmpty() && LETRA_PLACA.equals(Character.toString(placaM.charAt(0)))) {
			LocalDate today = LocalDate.now();
			DayOfWeek day = today.getDayOfWeek();
			Set<DayOfWeek> diasNoPermitidos = EnumSet.of(DayOfWeek.SUNDAY, DayOfWeek.MONDAY);
			return !diasNoPermitidos.contains(day);

		}
		return Boolean.TRUE;
	}

}
