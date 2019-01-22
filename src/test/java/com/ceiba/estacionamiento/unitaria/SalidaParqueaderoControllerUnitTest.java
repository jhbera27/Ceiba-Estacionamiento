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
import com.ceiba.estacinamiento.dominio.SalidaParqueadero;
import com.ceiba.estacionamiento.controller.SalidaParqueaderoController;
import com.ceiba.estacionamiento.persistencia.service.SalidaParqueaderoService;
import com.ceiba.estacionamiento.testdatabuilder.CalcularPrecioTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.SalidaParqueaderoTestDataBuilder;

public class SalidaParqueaderoControllerUnitTest {

	private SalidaParqueaderoService salidaParqueaderoService;
	private SalidaParqueaderoController salidaParqueaderoController;

	@Before
	public void setUp() {
		salidaParqueaderoService = Mockito.mock(SalidaParqueaderoService.class);
		salidaParqueaderoController = new SalidaParqueaderoController(salidaParqueaderoService);
	}

	@Test
	public void obtenerSalidasParqueaderoControllerTest() {
		SalidaParqueadero salidaParqueadero = new SalidaParqueaderoTestDataBuilder().build();
		List<SalidaParqueadero> listaSalida = new ArrayList<>();
		listaSalida.add(salidaParqueadero);

		Mockito.when(salidaParqueaderoService.obtenerSalidaVehiculosParqueadero()).thenReturn(listaSalida);
		List<SalidaParqueadero> listaSalidaConsultado = salidaParqueaderoController.obtenerSalidas();
		Assert.assertEquals(listaSalida, listaSalidaConsultado);
	}

	@Test
	public void registrarSalidaParqueaderoControllerTest() {
		SalidaParqueadero salidaParqueadero = new SalidaParqueaderoTestDataBuilder().build();
		verify(salidaParqueaderoService, never()).agregar(any(SalidaParqueadero.class));
		salidaParqueaderoController.crearSalidaParqueadero(salidaParqueadero);
	}

	@Test
	public void calcularValorControllerTest() {
		CalcularPrecio calcularPrecio = new CalcularPrecioTestDataBuilder().build();
		Mockito.when(salidaParqueaderoService.calcularPrecioAPagar(calcularPrecio.getVehiculoSalida(),
				calcularPrecio.getValorHoraVehiculo(), calcularPrecio.getValorDiaVehiculo()))
				.thenReturn(new BigDecimal(10000));
		BigDecimal precioCalculado = salidaParqueaderoController.calcularValor(calcularPrecio);
		Assert.assertEquals(new BigDecimal(10000), precioCalculado);
	}

}
