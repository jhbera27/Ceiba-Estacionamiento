package com.ceiba.estacionamiento.integracion;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.ceiba.establecimiento.enums.TipoVehiculoEnum;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

/**
 * clase que permite realizar las pruebas de integracion para la clase
 * SalidaParqueaderoPersistenciaRepository
 * 
 * @author jhon.bedoya
 *
 */
public class SalidaParqueaderoPersistenciaRepositoryIntegracion {
//	/**
//	 * instanciación del sistema de persistencia para las pruebas unitarias
//	 */
//	private SistemaDePersistencia sistemaDePersistencia;
//	/**
//	 * instanciación del repositorio de salida de vehiculos del parqueaderos para
//	 * las pruebas unitarias
//	 */
//	private SalidaParqueaderoRepository salidaParqueaderoRepository;
//	/**
//	 * instanciación del repositorio de vehiculos para las pruebas unitarias
//	 */
//	private VehiculoRepository vehiculoRepository;
//
//	/**
//	 * método encargado de inicializar el contexto de pertistencia para las pruebas
//	 * unitarias
//	 */
//	@Before
//	public void setUp() {
//		sistemaDePersistencia = new SistemaDePersistencia();
//		salidaParqueaderoRepository = sistemaDePersistencia.obtenerSalidaParqueaderoRepository();
//		vehiculoRepository = sistemaDePersistencia.obtenerVehiculoRepository();
//		sistemaDePersistencia.iniciar();
//	}
//
//	/**
//	 * Método encargado de verificar el metodo calcularPrecioAPagar y que el precio
//	 * sea cobrado por horas
//	 * 
//	 * @throws ParseException, excepcion generada al intentar convertir una fecha
//	 */
//	@Test
//	public void calcularPrecioAPagarPorHoras() throws ParseException {
//		// arrange
//		Date fecha = new Date();
//		Calendar fechaC = Calendar.getInstance();
//		fechaC.setTime(fecha);
//		fechaC.set(Calendar.HOUR, fechaC.get(Calendar.HOUR) - 3);
//		fechaC.set(Calendar.MINUTE, fechaC.get(Calendar.MINUTE) - 20);
//		Vehiculo vehiculo = new VehiculoTestDataBuilder().conFechaIngreso(fechaC.getTime()).build();
//		vehiculoRepository.agregar(vehiculo);
//		// act
//		BigDecimal precioPagado = salidaParqueaderoRepository.calcularPrecioAPagar(vehiculo,
//				SalidaParqueaderoPersistenciaRepository.VALOR_HORA_MOTO,
//				SalidaParqueaderoPersistenciaRepository.VALOR_DIA_MOTO);
//		// assert
//		Assert.assertNotNull(precioPagado);
//		Assert.assertEquals(new BigDecimal(4000), precioPagado);
//	}
//
//	/**
//	 * Método encargado de verificar el metodo calcularPrecioAPagar cuando es menor
//	 * a una hora
//	 * 
//	 * @throws ParseException, excepcion generada al intentar convertir una fecha
//	 */
//	@Test
//	public void calcularPrecioAPagarUnaHora() throws ParseException {
//		// arrange
//		Date fecha = new Date();
//		Vehiculo vehiculo = new VehiculoTestDataBuilder().conFechaIngreso(fecha).build();
//		vehiculoRepository.agregar(vehiculo);
//		// act
//		BigDecimal precioPagado = salidaParqueaderoRepository.calcularPrecioAPagar(vehiculo,
//				SalidaParqueaderoPersistenciaRepository.VALOR_HORA_MOTO,
//				SalidaParqueaderoPersistenciaRepository.VALOR_DIA_MOTO);
//		// assert
//		Assert.assertNotNull(precioPagado);
//		Assert.assertEquals(new BigDecimal(2500), precioPagado);
//	}
//
//	/**
//	 * Método encargado de verificar el metodo calcularPrecioAPagar y que el precio
//	 * sea cobrado por horas
//	 * 
//	 * @throws ParseException, excepcion generada al intentar convertir una fecha
//	 */
//	@Test
//	public void calcularPrecioAPagarPorDia() throws ParseException {
//		// arrange
//		datosCalcularPrecioApagar(0, 10);
//		Vehiculo vehiculo = vehiculoRepository.buscarVehiculoPorPlaca("PQT29G");
//		// act
//		BigDecimal precioPagado = salidaParqueaderoRepository.calcularPrecioAPagar(vehiculo,
//				SalidaParqueaderoPersistenciaRepository.VALOR_HORA_CARRO,
//				SalidaParqueaderoPersistenciaRepository.VALOR_DIA_CARRO);
//		// assert
//		Assert.assertNotNull(precioPagado);
//		Assert.assertEquals(new BigDecimal(8000), precioPagado);
//	}
//
//	/**
//	 * Método encargado de verificar el metodo calcularPrecioAPagar y que el precio
//	 * sea cobrado por por dias y horas
//	 * 
//	 * @throws ParseException, excepcion generada al intentar convertir una fecha
//	 */
//	@Test
//	public void calcularPrecioAPagarPorMasDeUnDia() throws ParseException {
//		// arrange
//		datosCalcularPrecioApagar(1, 1);
//		Vehiculo vehiculo = vehiculoRepository.buscarVehiculoPorPlaca("PQT29G");
//		// act
//		BigDecimal precioPagado = salidaParqueaderoRepository.calcularPrecioAPagar(vehiculo,
//				SalidaParqueaderoPersistenciaRepository.VALOR_HORA_CARRO,
//				SalidaParqueaderoPersistenciaRepository.VALOR_DIA_CARRO);
//		// assert
//		Assert.assertNotNull(precioPagado);
//		Assert.assertEquals(new BigDecimal(10000), precioPagado);
//	}
//
//	/**
//	 * Método encargado de verificar el metodo calcularPrecioAPagar y que el precio
//	 * sea cobrado por horas
//	 * 
//	 * @throws ParseException, excepcion generada al intentar convertir una fecha
//	 */
//	@Test
//	public void calcularPrecioAPagarPorMasDeUnDiaConHoras() throws ParseException {
//		// arrange
//		datosCalcularPrecioApagar(1, 10);
//		Vehiculo vehiculo = vehiculoRepository.buscarVehiculoPorPlaca("PQT29G");
//		// act
//		BigDecimal precioPagado = salidaParqueaderoRepository.calcularPrecioAPagar(vehiculo,
//				SalidaParqueaderoPersistenciaRepository.VALOR_HORA_CARRO,
//				SalidaParqueaderoPersistenciaRepository.VALOR_DIA_CARRO);
//		// assert
//		Assert.assertNotNull(precioPagado);
//		Assert.assertEquals(new BigDecimal(18000), precioPagado);
//	}
//
//	/**
//	 * método encargado de crear los datos para la prueba CalcularPrecioAPagar
//	 * 
//	 * @param             DiasRestar, numero de dias a restar
//	 * @param horasRestar numero de horas a restar
//	 */
//	public void datosCalcularPrecioApagar(int DiasRestar, int horasRestar) {
//		Date fecha = new Date();
//		Calendar fechaC = Calendar.getInstance();
//		fechaC.setTime(fecha);
//		fechaC.set(Calendar.DAY_OF_MONTH, fechaC.get(Calendar.DAY_OF_MONTH) - DiasRestar);
//		fechaC.set(Calendar.HOUR, fechaC.get(Calendar.HOUR) - horasRestar);
//		Vehiculo vehiculo = new VehiculoTestDataBuilder().conFechaIngreso(fechaC.getTime())
//				.conTipo(TipoVehiculoEnum.CARRO.name()).build();
//		vehiculoRepository.agregar(vehiculo);
//	}
//
//	/**
//	 * método encargado de finalizar la conexion para las pruebas unitarias
//	 */
//	@After
//	public void tearDown() {
//		sistemaDePersistencia.terminar();
//	}
}
