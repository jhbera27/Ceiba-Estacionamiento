package com.ceiba.estacionamiento.integracion;

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

import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.controller.VehiculoController;
import com.ceiba.estacionamiento.estacionamiento.EstacionamientoApplication;
import com.ceiba.estacionamiento.persistencia.service.VehiculoService;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = EstacionamientoApplication.class)
public class VehiculoControllerIntegrationUnit {
	
	private VehiculoController vehiculoController;
	
	@Autowired
	private VehiculoService vehiculoService;

	@Before
	public void setUp() {
		vehiculoController = new VehiculoController(vehiculoService);
	}

//	@Test
//	@Transactional
//	@Rollback(true)
	public void obtenerVehiculosControllerTest() {
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PTT26H").build();
		vehiculoController.agregarVehiculoParqueadero(vehiculoTest);
		List<Vehiculo> listaVehiculosObtenidos = vehiculoController.obtenerVehiculos();
		Assert.assertEquals(1, listaVehiculosObtenidos.size());
	}
	
//	@Test
//	@Transactional
//	@Rollback(true)
	public void crearVehiculoControllerTest() {
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PTT26H").build();
		vehiculoController.agregarVehiculoParqueadero(vehiculoTest);
		List<Vehiculo> listaVehiculosObtenidos = vehiculoController.obtenerVehiculos();
		Assert.assertEquals(listaVehiculosObtenidos.get(0).getPlaca(), vehiculoTest.getPlaca());
	}

}
