package com.ceiba.estacionamiento.integracion;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ceiba.estacinamiento.dominio.CalcularPrecio;
import com.ceiba.estacinamiento.dominio.SalidaVehiculo;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.controller.SalidaVehiculoController;
import com.ceiba.estacionamiento.controller.VehiculoController;
import com.ceiba.estacionamiento.estacionamiento.EstacionamientoApplication;
import com.ceiba.estacionamiento.persistencia.service.SalidaVehiculoService;
import com.ceiba.estacionamiento.persistencia.service.VehiculoService;
import com.ceiba.estacionamiento.testdatabuilder.CalcularPrecioTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.SalidaVehiculoTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = EstacionamientoApplication.class)
public class SalidaVehiculoControllerIntegrationUnit {

	@Autowired
	private SalidaVehiculoService salidaParqueaderoService;
	private SalidaVehiculoController salidaParqueaderoController;

	private VehiculoController vehiculoController;

	@Autowired
	private VehiculoService vehiculoService;

	@Before
	public void setUp() {
		salidaParqueaderoController = new SalidaVehiculoController(salidaParqueaderoService);
		vehiculoController = new VehiculoController(vehiculoService);
	}

//	@Test
//	@Transactional
//	@Rollback(true)
	public void obtenerSalidasParqueaderoControllerTest() {
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PTT26H").build();
		vehiculoController.agregarVehiculoParqueadero(vehiculoTest);
		List<Vehiculo> listaVehiculosObtenidos = vehiculoController.obtenerVehiculos();
		SalidaVehiculo salidaParqueadero = new SalidaVehiculoTestDataBuilder()
				.conVehiculoSalida(listaVehiculosObtenidos.get(0)).build();
		salidaParqueaderoController.registrarSalidaVehiculo(salidaParqueadero);
		List<SalidaVehiculo> listaSalidaConsultado = salidaParqueaderoController.obtenerHistorialVehiculos();
		Assert.assertEquals(1, listaSalidaConsultado.size());
	}

//	@Test
//	@Transactional
//	@Rollback(true)
	public void registrarSalidaVehiculoControllerTest() {
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PTT26H").build();
		vehiculoController.agregarVehiculoParqueadero(vehiculoTest);
		List<Vehiculo> listaVehiculosObtenidos = vehiculoController.obtenerVehiculos();
		SalidaVehiculo salidaParqueadero = new SalidaVehiculoTestDataBuilder()
				.conVehiculoSalida(listaVehiculosObtenidos.get(0)).build();
		salidaParqueaderoController.registrarSalidaVehiculo(salidaParqueadero);
		List<SalidaVehiculo> listaSalidaConsultado = salidaParqueaderoController.obtenerHistorialVehiculos();
		Assert.assertEquals(salidaParqueadero.getVehiculoSalida().getPlaca(),
				listaSalidaConsultado.get(0).getVehiculoSalida().getPlaca());
		Assert.assertNotNull(listaSalidaConsultado.get(0).getFechaSalida());
		Assert.assertNotNull(listaSalidaConsultado.get(0).getPrecioPagado());
	}

//	@Test
//	@Transactional
//	@Rollback(true)
	public void calcularValorAPagarControllerTest() {
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PTT26H").build();
		vehiculoController.agregarVehiculoParqueadero(vehiculoTest);
		List<Vehiculo> listaVehiculosObtenidos = vehiculoController.obtenerVehiculos();
		CalcularPrecio calcularPrecio = new CalcularPrecioTestDataBuilder()
				.conVehiculoSalida(listaVehiculosObtenidos.get(0)).build();
		BigDecimal precioCalculado = salidaParqueaderoController.calcularPrecioAPagar(calcularPrecio);
		Assert.assertEquals(new BigDecimal(2500), precioCalculado);
	}

}
