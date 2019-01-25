package com.ceiba.estacionamiento.unitaria;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.estacinamiento.dominio.CalcularPrecio;
import com.ceiba.estacinamiento.dominio.SalidaVehiculo;
import com.ceiba.estacionamiento.controller.SalidaVehiculoController;
import com.ceiba.estacionamiento.persistencia.service.SalidaVehiculoService;
import com.ceiba.estacionamiento.testdatabuilder.CalcularPrecioTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.SalidaVehiculoTestDataBuilder;

public class SalidaVehiculoControllerUnitTest {

	private SalidaVehiculoService salidaParqueaderoService;
	private SalidaVehiculoController salidaParqueaderoController;

	@Before
	public void setUp() {
		salidaParqueaderoService = Mockito.mock(SalidaVehiculoService.class);
		salidaParqueaderoController = new SalidaVehiculoController(salidaParqueaderoService);
	}

	@Test
	public void obtenerSalidasVehiculosControllerTest() {
		SalidaVehiculo salidaParqueadero = new SalidaVehiculoTestDataBuilder().build();
		List<SalidaVehiculo> listaSalida = new ArrayList<>();
		listaSalida.add(salidaParqueadero);

		Mockito.when(salidaParqueaderoService.obtenerHistorialVehiculos()).thenReturn(listaSalida);
		List<SalidaVehiculo> listaSalidaConsultado = salidaParqueaderoController.obtenerHistorialVehiculos();
		Assert.assertEquals(listaSalida, listaSalidaConsultado);
	}

	@Test
	public void registrarSalidaVehiculoControllerTest() {
		SalidaVehiculo salidaParqueadero = new SalidaVehiculoTestDataBuilder().build();
		verify(salidaParqueaderoService, never()).registrarSalidaVehiculo(any(SalidaVehiculo.class));
		salidaParqueaderoController.registrarSalidaVehiculo(salidaParqueadero);
	}

	@Test
	public void calcularValorAPagarControllerTest() {
		CalcularPrecio calcularPrecio = new CalcularPrecioTestDataBuilder().build();
		Mockito.when(salidaParqueaderoService.calcularPrecioAPagar(calcularPrecio.getVehiculoSalida(),
				calcularPrecio.getValorHoraVehiculo(), calcularPrecio.getValorDiaVehiculo()))
				.thenReturn(new BigDecimal(10000));
		BigDecimal precioCalculado = salidaParqueaderoController.calcularPrecioAPagar(calcularPrecio);
		Assert.assertEquals(new BigDecimal(10000), precioCalculado);
	}

}
