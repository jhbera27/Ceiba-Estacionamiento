package com.ceiba.estacionamiento.persistencia.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.ceiba.establecimiento.enums.TipoVehiculoEnum;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacinamiento.dominio.Vigilante;
import com.ceiba.estacionamiento.dominio.excepcion.VehiculoException;
import com.ceiba.estacionamiento.persistencia.builder.VehiculoBuilder;
import com.ceiba.estacionamiento.persistencia.entity.VehiculoEntity;
import com.ceiba.estacionamiento.persistencia.repositorio.VehiculoRepository;
import com.ceiba.estacionamiento.persistencia.service.VehiculoService;

@Service
@Transactional
public class VehiculoServiceImpl implements VehiculoService {

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
	public void agregar(Vehiculo vehiculo) {
		validarCreacionVehiculo(vehiculo);
		Vigilante.verificarPlaca(vehiculo.getPlaca());
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
		VehiculoEntity vehiculo;
		try {
			vehiculo = vehiculoRepository.buscarVehiculoPorPlaca(placa);
		} catch (NoResultException e) {
			vehiculo = null;
		}
		return vehiculo != null ? VehiculoBuilder.convertirADominio(vehiculo) : null;
	}
	
	/**
	 * Método encargado de verificar si se puede registrar erl vehiculo dado la
	 * regla de negocio que solo se permiten 20 carro y 10 motos simultaneamente
	 * 
	 * @param nuevoVehiculo, el vehiculo a crear
	 */
	public void validarCreacionVehiculo(Vehiculo nuevoVehiculo) {
		List<Vehiculo> listaVehiculos = obtenerVehiculos();
		List<Vehiculo> listaVehiculosPorTipo = listaVehiculos.stream()
				.filter(vehiculo -> vehiculo.getTipo().equals(nuevoVehiculo.getTipo())).collect(Collectors.toList());

		if (TipoVehiculoEnum.MOTO.name().equals(nuevoVehiculo.getTipo())
				&& Vigilante.NUMERO_MAXIMO_MOTOS  < listaVehiculosPorTipo.size() ) {
			throw new VehiculoException(Vigilante.SIN_CUPO);
		}
		if (TipoVehiculoEnum.CARRO.name().equals(nuevoVehiculo.getTipo())
				&& Vigilante.NUMERO_MAXIMO_CARROS < listaVehiculosPorTipo.size()  ) {
			throw new VehiculoException(Vigilante.SIN_CUPO);
		}
	}

}
