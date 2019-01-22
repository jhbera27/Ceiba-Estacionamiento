package com.ceiba.estacionamiento.unitaria;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.controller.VehiculoController;
import com.ceiba.estacionamiento.persistencia.service.VehiculoService;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

public class VehiculoControllerUnitTest {

	private VehiculoController vehiculoController;
	private VehiculoService vehiculoService;

	@Before
	public void setUp() {
		vehiculoService = Mockito.mock(VehiculoService.class);
		vehiculoController = new VehiculoController(vehiculoService);
	}

	@Test
	public void obtenerVehiculosControllerTest() {
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PTT26H").build();
		List<Vehiculo> listaVehiculos = new ArrayList<>();
		listaVehiculos.add(vehiculoTest);

		Mockito.when(vehiculoService.obtenerVehiculos()).thenReturn(listaVehiculos);
		List<Vehiculo> listaVehiculosObtenidos = vehiculoController.obtenerVehiculos();
		Assert.assertEquals(listaVehiculosObtenidos, listaVehiculos);
	}
	
	@Test
	public void crearVehiculoControllerTest() {
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PTT26H").build();
		verify(vehiculoService, never()).agregar(any(Vehiculo.class));  
		vehiculoController.crearVehiculo(vehiculoTest);
	}
}
