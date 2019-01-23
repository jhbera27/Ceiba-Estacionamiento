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
import com.ceiba.estacinamiento.dominio.SalidaParqueadero;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.estacionamiento.EstacionamientoApplication;
import com.ceiba.estacionamiento.persistencia.repositorio.SalidaParqueaderoRepository;
import com.ceiba.estacionamiento.persistencia.repositorio.VehiculoRepository;
import com.ceiba.estacionamiento.persistencia.service.impl.SalidaParqueaderoServiceImpl;
import com.ceiba.estacionamiento.persistencia.service.impl.VehiculoServiceImpl;
import com.ceiba.estacionamiento.testdatabuilder.SalidaParqueaderoTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EstacionamientoApplication.class)
public class SalidaParqueaderoServiceIntegrationTest {

	private SalidaParqueaderoServiceImpl salidaParqueaderoService;
	
	private VehiculoServiceImpl vehiculoService;
	
	@Autowired
	private SalidaParqueaderoRepository salidaParqueaderoRepository;
	@Autowired
	private VehiculoRepository vehiculoRepository;

	/**
	 * método encargado de inicializar el contexto de pertistencia para las pruebas
	 * unitarias
	 */
	@Before
	public void setUp() {
		vehiculoService = new VehiculoServiceImpl(vehiculoRepository);
		salidaParqueaderoService = new SalidaParqueaderoServiceImpl(salidaParqueaderoRepository, vehiculoRepository);
	}

	/**
	 * Método encargado de verificar que se almacene correctamente una salida de un
	 * vehiculo tipo moto del parqueadero
	 * 
	 */
//	@Test
//	@Transactional
//	@Rollback(true)
	public void agregarSalidaParqueaderoMotoExitoTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conTipo(TipoVehiculoEnum.CARRO.name()).build();
		vehiculoService.agregar(vehiculo);
		Vehiculo vehiculoEncontrado = vehiculoService.buscarVehiculoPorPlaca(vehiculo.getPlaca());
		SalidaParqueadero salidaParqueadero = new SalidaParqueaderoTestDataBuilder().conVehiculoSalida(vehiculoEncontrado)
				.build();
		// act
		salidaParqueaderoService.agregar(salidaParqueadero);
		// assert
		List<SalidaParqueadero> listaSalidaParqueadero = salidaParqueaderoService.obtenerSalidaVehiculosParqueadero();
		Assert.assertNotNull(listaSalidaParqueadero);
		Assert.assertEquals(1, listaSalidaParqueadero.size());
	}

	/**
	 * Método encargado de verificar que se almacene correctamente una salida de un
	 * vehiculo tipo carro del parqueadero
	 */
//	@Test
//	@Transactional
//	@Rollback(true)
	public void agregarSalidaParqueaderoCarroExitoTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conTipo(TipoVehiculoEnum.CARRO.name()).build();
		vehiculoService.agregar(vehiculo);
		Vehiculo vehiculoEncontrado = vehiculoService.buscarVehiculoPorPlaca(vehiculo.getPlaca());
		SalidaParqueadero salidaParqueadero = new SalidaParqueaderoTestDataBuilder().conVehiculoSalida(vehiculoEncontrado)
				.build();
		// act
		salidaParqueaderoService.agregar(salidaParqueadero);
		// assert
		List<SalidaParqueadero> listaSalidaParqueadero = salidaParqueaderoService.obtenerSalidaVehiculosParqueadero();
		Assert.assertNotNull(listaSalidaParqueadero);
		Assert.assertEquals(1, listaSalidaParqueadero.size());

	}

	/**
	 * Método encargado de verificar que se obtengan las salidad de vehiculos del
	 * parqueadero
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
//	@Test
//	@Transactional
//	@Rollback(true)
	public void obtenerSalidaVehiculosParqueaderoTest() throws ParseException {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conTipo(TipoVehiculoEnum.CARRO.name()).build();
		vehiculoService.agregar(vehiculo);
		Vehiculo vehiculoEncontrado = vehiculoService.buscarVehiculoPorPlaca(vehiculo.getPlaca());
		SalidaParqueadero salidaParqueadero = new SalidaParqueaderoTestDataBuilder().conVehiculoSalida(vehiculoEncontrado)
				.build();
		salidaParqueaderoService.agregar(salidaParqueadero);
		// act
		List<SalidaParqueadero> listaSalidaParqueadero = salidaParqueaderoService.obtenerSalidaVehiculosParqueadero();
		// assert
		Assert.assertNotNull(listaSalidaParqueadero);
		Assert.assertEquals(1, listaSalidaParqueadero.size());
	}

	/**
	 * Método encargado de verificar el metodo calcularPrecioAPagar y que el precio
	 * sea cobrado por horas
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
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
				SalidaParqueaderoServiceImpl.VALOR_HORA_MOTO, SalidaParqueaderoServiceImpl.VALOR_DIA_MOTO);
		// assert
		Assert.assertNotNull(precioAPagado);
		Assert.assertEquals(new BigDecimal(4000), precioAPagado);
	}

	/**
	 * Método encargado de verificar el metodo calcularPrecioAPagar y que el precio
	 * sea cobrado por horas
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
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
				SalidaParqueaderoServiceImpl.VALOR_HORA_CARRO, SalidaParqueaderoServiceImpl.VALOR_DIA_CARRO);
		// assert
		Assert.assertNotNull(precioAPagado);
		Assert.assertEquals(new BigDecimal(8000), precioAPagado);
	}

	/**
	 * Método encargado de verificar el metodo calcularPrecioAPagar y que el precio
	 * sea cobrado por por dias y horas
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
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
				SalidaParqueaderoServiceImpl.VALOR_HORA_CARRO, SalidaParqueaderoServiceImpl.VALOR_DIA_CARRO);
		// assert
		Assert.assertNotNull(precioAPagado);
		Assert.assertEquals(new BigDecimal(11000), precioAPagado);
	}

	/**
	 * Método encargado de verificar el metodo calcularPrecioAPagar y que el precio
	 * sea cobrado por horas
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
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
				SalidaParqueaderoServiceImpl.VALOR_HORA_CARRO, SalidaParqueaderoServiceImpl.VALOR_DIA_CARRO);
		// assert
		Assert.assertNotNull(precioAPagado);
		Assert.assertEquals(new BigDecimal(18000), precioAPagado);
	}

}
