package com.ceiba.estacionamiento.integracion;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
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

import com.ceiba.establecimiento.enums.TipoVehiculoEnum;
import com.ceiba.estacinamiento.dominio.SalidaVehiculo;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.estacionamiento.EstacionamientoApplication;
import com.ceiba.estacionamiento.persistencia.repositorio.SalidaVehiculoRepository;
import com.ceiba.estacionamiento.persistencia.repositorio.VehiculoRepository;
import com.ceiba.estacionamiento.persistencia.service.impl.SalidaVehiculoServiceImpl;
import com.ceiba.estacionamiento.persistencia.service.impl.VehiculoServiceImpl;
import com.ceiba.estacionamiento.testdatabuilder.SalidaVehiculoTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = EstacionamientoApplication.class)
public class SalidaVehiculoServiceIntegrationTest {

	private SalidaVehiculoServiceImpl salidaParqueaderoService;
	
	private VehiculoServiceImpl vehiculoService;
	
	@Autowired
	private SalidaVehiculoRepository salidaParqueaderoRepository;
	@Autowired
	private VehiculoRepository vehiculoRepository;


	@Before
	public void setUp() {
		vehiculoService = new VehiculoServiceImpl(vehiculoRepository);
		salidaParqueaderoService = new SalidaVehiculoServiceImpl(salidaParqueaderoRepository, vehiculoRepository);
	}

//	@Test
//	@Transactional
//	@Rollback(true)
	public void agregarSalidaVehiculoTipoMotoExitoTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conTipo(TipoVehiculoEnum.CARRO.name()).build();
		vehiculoService.agregarVehiculoParqueadero(vehiculo);
		Vehiculo vehiculoEncontrado = vehiculoService.buscarVehiculoPorPlaca(vehiculo.getPlaca());
		SalidaVehiculo salidaParqueadero = new SalidaVehiculoTestDataBuilder().conVehiculoSalida(vehiculoEncontrado)
				.build();
		// act
		salidaParqueaderoService.registrarSalidaVehiculo(salidaParqueadero);
		// assert
		List<SalidaVehiculo> listaSalidaParqueadero = salidaParqueaderoService.obtenerHistorialVehiculos();
		Assert.assertNotNull(listaSalidaParqueadero);
		Assert.assertEquals(1, listaSalidaParqueadero.size());
	}

//	@Test
//	@Transactional
//	@Rollback(true)
	public void agregarSalidaVehiculoTipoCarroExitoTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conTipo(TipoVehiculoEnum.CARRO.name()).build();
		vehiculoService.agregarVehiculoParqueadero(vehiculo);
		Vehiculo vehiculoEncontrado = vehiculoService.buscarVehiculoPorPlaca(vehiculo.getPlaca());
		SalidaVehiculo salidaParqueadero = new SalidaVehiculoTestDataBuilder().conVehiculoSalida(vehiculoEncontrado)
				.build();
		// act
		salidaParqueaderoService.registrarSalidaVehiculo(salidaParqueadero);
		// assert
		List<SalidaVehiculo> listaSalidaParqueadero = salidaParqueaderoService.obtenerHistorialVehiculos();
		Assert.assertNotNull(listaSalidaParqueadero);
		Assert.assertEquals(1, listaSalidaParqueadero.size());

	}

//	@Test
//	@Transactional
//	@Rollback(true)
	public void obtenerSalidaVehiculosParqueaderoTest() throws ParseException {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conTipo(TipoVehiculoEnum.CARRO.name()).build();
		vehiculoService.agregarVehiculoParqueadero(vehiculo);
		Vehiculo vehiculoEncontrado = vehiculoService.buscarVehiculoPorPlaca(vehiculo.getPlaca());
		SalidaVehiculo salidaParqueadero = new SalidaVehiculoTestDataBuilder().conVehiculoSalida(vehiculoEncontrado)
				.build();
		salidaParqueaderoService.registrarSalidaVehiculo(salidaParqueadero);
		// act
		List<SalidaVehiculo> listaSalidaParqueadero = salidaParqueaderoService.obtenerHistorialVehiculos();
		// assert
		Assert.assertNotNull(listaSalidaParqueadero);
		Assert.assertEquals(1, listaSalidaParqueadero.size());
	}


//	@Test
	public void calcularPrecioAPagarPorHoras() throws ParseException {
		// arrange
		Date fecha = new Date();
		Calendar fechaC = Calendar.getInstance();
		fechaC.setTime(fecha);
		fechaC.set(Calendar.HOUR, fechaC.get(Calendar.HOUR) - 3);
		fechaC.set(Calendar.MINUTE, fechaC.get(Calendar.MINUTE) - 20);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conFechaIngreso(fechaC.getTime()).build();
		// act
		BigDecimal precioAPagado = salidaParqueaderoService.calcularPrecioAPagar(vehiculo,
				SalidaVehiculoServiceImpl.VALOR_HORA_MOTO, SalidaVehiculoServiceImpl.VALOR_DIA_MOTO);
		// assert
		Assert.assertNotNull(precioAPagado);
		Assert.assertEquals(new BigDecimal(4000), precioAPagado);
	}

//	@Test
	public void calcularPrecioAPagarPorDia() throws ParseException {
		// arrange
		Date fecha = new Date();
		Calendar fechaC = Calendar.getInstance();
		fechaC.setTime(fecha);
		fechaC.set(Calendar.HOUR, fechaC.get(Calendar.HOUR) - 10);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conFechaIngreso(fechaC.getTime())
				.conTipo(TipoVehiculoEnum.CARRO.name()).build();
		// act
		BigDecimal precioAPagado = salidaParqueaderoService.calcularPrecioAPagar(vehiculo,
				SalidaVehiculoServiceImpl.VALOR_HORA_CARRO, SalidaVehiculoServiceImpl.VALOR_DIA_CARRO);
		// assert
		Assert.assertNotNull(precioAPagado);
		Assert.assertEquals(new BigDecimal(8000), precioAPagado);
	}

//	@Test
	public void calcularPrecioAPagarPorMasDeUnDia() throws ParseException {
		// arrange
		Date fecha = new Date();
		Calendar fechaC = Calendar.getInstance();
		fechaC.setTime(fecha);
		fechaC.set(Calendar.DATE, fechaC.get(Calendar.DATE) - 1);
		fechaC.set(Calendar.HOUR, fechaC.get(Calendar.HOUR) - 2);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conFechaIngreso(fechaC.getTime())
				.conTipo(TipoVehiculoEnum.CARRO.name()).build();
		// act
		BigDecimal precioAPagado = salidaParqueaderoService.calcularPrecioAPagar(vehiculo,
				SalidaVehiculoServiceImpl.VALOR_HORA_CARRO, SalidaVehiculoServiceImpl.VALOR_DIA_CARRO);
		// assert
		Assert.assertNotNull(precioAPagado);
		Assert.assertEquals(new BigDecimal(11000), precioAPagado);
	}

//	@Test
	public void calcularPrecioAPagarPorMasDeUnDiaConHoras() throws ParseException {
		// arrange
		Date fecha = new Date();
		Calendar fechaC = Calendar.getInstance();
		fechaC.setTime(fecha);
		fechaC.set(Calendar.DATE, fechaC.get(Calendar.DATE) - 1);
		fechaC.set(Calendar.HOUR, fechaC.get(Calendar.HOUR) - 10);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conFechaIngreso(fechaC.getTime())
				.conTipo(TipoVehiculoEnum.CARRO.name()).build();
		// act
		BigDecimal precioAPagado = salidaParqueaderoService.calcularPrecioAPagar(vehiculo,
				SalidaVehiculoServiceImpl.VALOR_HORA_CARRO, SalidaVehiculoServiceImpl.VALOR_DIA_CARRO);
		// assert
		Assert.assertNotNull(precioAPagado);
		Assert.assertEquals(new BigDecimal(18000), precioAPagado);
	}

}
