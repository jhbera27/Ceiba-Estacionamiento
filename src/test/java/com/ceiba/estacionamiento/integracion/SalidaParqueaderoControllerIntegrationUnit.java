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
import com.ceiba.estacinamiento.dominio.SalidaParqueadero;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.controller.SalidaParqueaderoController;
import com.ceiba.estacionamiento.controller.VehiculoController;
import com.ceiba.estacionamiento.estacionamiento.EstacionamientoApplication;
import com.ceiba.estacionamiento.persistencia.service.SalidaParqueaderoService;
import com.ceiba.estacionamiento.persistencia.service.VehiculoService;
import com.ceiba.estacionamiento.testdatabuilder.CalcularPrecioTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.SalidaParqueaderoTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = EstacionamientoApplication.class)
public class SalidaParqueaderoControllerIntegrationUnit {

	@Autowired
	private SalidaParqueaderoService salidaParqueaderoService;
	private SalidaParqueaderoController salidaParqueaderoController;

	private VehiculoController vehiculoController;

	@Autowired
	private VehiculoService vehiculoService;

	@Before
	public void setUp() {
		salidaParqueaderoController = new SalidaParqueaderoController(salidaParqueaderoService);
		vehiculoController = new VehiculoController(vehiculoService);
	}

//	@Test
//	@Transactional
//	@Rollback(true)
	public void obtenerSalidasParqueaderoControllerTest() {
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PTT26H").build();
		vehiculoController.crearVehiculo(vehiculoTest);
		List<Vehiculo> listaVehiculosObtenidos = vehiculoController.obtenerVehiculos();
		SalidaParqueadero salidaParqueadero = new SalidaParqueaderoTestDataBuilder()
				.conVehiculoSalida(listaVehiculosObtenidos.get(0)).build();
		salidaParqueaderoController.crearSalidaParqueadero(salidaParqueadero);
		List<SalidaParqueadero> listaSalidaConsultado = salidaParqueaderoController.obtenerSalidas();
		Assert.assertEquals(1, listaSalidaConsultado.size());
	}

//	@Test
//	@Transactional
//	@Rollback(true)
	public void registrarSalidaParqueaderoControllerTest() {
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PTT26H").build();
		vehiculoController.crearVehiculo(vehiculoTest);
		List<Vehiculo> listaVehiculosObtenidos = vehiculoController.obtenerVehiculos();
		SalidaParqueadero salidaParqueadero = new SalidaParqueaderoTestDataBuilder()
				.conVehiculoSalida(listaVehiculosObtenidos.get(0)).build();
		salidaParqueaderoController.crearSalidaParqueadero(salidaParqueadero);
		List<SalidaParqueadero> listaSalidaConsultado = salidaParqueaderoController.obtenerSalidas();
		Assert.assertEquals(salidaParqueadero.getVehiculoSalida().getPlaca(),
				listaSalidaConsultado.get(0).getVehiculoSalida().getPlaca());
		Assert.assertNotNull(listaSalidaConsultado.get(0).getFechaSalida());
		Assert.assertNotNull(listaSalidaConsultado.get(0).getPrecioPagado());
	}

//	@Test
//	@Transactional
//	@Rollback(true)
	public void calcularValorControllerTest() {
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PTT26H").build();
		vehiculoController.crearVehiculo(vehiculoTest);
		List<Vehiculo> listaVehiculosObtenidos = vehiculoController.obtenerVehiculos();
		CalcularPrecio calcularPrecio = new CalcularPrecioTestDataBuilder()
				.conVehiculoSalida(listaVehiculosObtenidos.get(0)).build();
		BigDecimal precioCalculado = salidaParqueaderoController.calcularValor(calcularPrecio);
		Assert.assertEquals(new BigDecimal(2500), precioCalculado);
	}

}
