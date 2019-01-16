package com.ceiba.estacionamiento.unitaria;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.ceiba.establecimiento.enums.TipoVehiculoEnum;
import com.ceiba.estacinamiento.dominio.SalidaParqueadero;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.dominio.repositorio.SalidaParqueaderoRepository;
import com.ceiba.estacionamiento.dominio.repositorio.VehiculoRepository;
import com.ceiba.estacionamiento.persistencia.repositorio.SalidaParqueaderoPersistenciaRepository;
import com.ceiba.estacionamiento.sistema.SistemaDePersistencia;
import com.ceiba.estacionamiento.testdatabuilder.SalidaParqueaderoTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

/**
 * clase que permite realizar las pruebas unitarias para la clase
 * SalidaParqueaderoPersistenciaRepository
 * 
 * @author jhon.bedoya
 *
 */
public class SalidaParqueaderoPersistenciaRepositoryTest {
	/**
	 * instanciaci�n del sistema de persistencia para las pruebas unitarias
	 */
	private SistemaDePersistencia sistemaDePersistencia;
	/**
	 * instanciaci�n del repositorio de salida de vehiculos del parqueaderos para
	 * las pruebas unitarias
	 */
	private SalidaParqueaderoRepository salidaParqueaderoRepository;
	/**
	 * instanciaci�n del repositorio de vehiculos para las pruebas unitarias
	 */
	private VehiculoRepository vehiculoRepository;

	/**
	 * m�todo encargado de inicializar el contexto de pertistencia para las pruebas
	 * unitarias
	 */
	@Before
	public void setUp() {
		sistemaDePersistencia = new SistemaDePersistencia();
		salidaParqueaderoRepository = sistemaDePersistencia.obtenerSalidaParqueaderoRepository();
		vehiculoRepository = sistemaDePersistencia.obtenerVehiculoRepository();
		sistemaDePersistencia.iniciar();
	}

	/**
	 * M�todo encargado de verificar que se almacene correctamente una salida de un
	 * vehiculo tipo moto del parqueadero
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
	@Test
	public void agregarSalidaParqueaderoMotoExitoTest() throws ParseException {
		// arrange
		datosAgregarSalidaParqueaderoTest("13/01/2019", TipoVehiculoEnum.MOTO);
		Vehiculo vehiculoConsultado = vehiculoRepository.buscarVehiculoPorPlaca("PQT29G");
		SalidaParqueadero salidaParqueadero = new SalidaParqueaderoTestDataBuilder()
				.conVehiculoSalida(vehiculoConsultado).build();
		int size = 1;
		// act
		salidaParqueaderoRepository.agregar(salidaParqueadero);
		// assert
		List<SalidaParqueadero> listaSalidaParqueadero = salidaParqueaderoRepository
				.obtenerSalidaVehiculosParqueadero();
		Assert.assertNotNull(listaSalidaParqueadero);
		Assert.assertEquals(size, listaSalidaParqueadero.size());
	}

	/**
	 * M�todo encargado de verificar que se almacene correctamente una salida de un
	 * vehiculo tipo carro del parqueadero
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
	@Test
	public void agregarSalidaParqueaderoCarroExitoTest() throws ParseException {
		// arrange
		datosAgregarSalidaParqueaderoTest("13/01/2019", TipoVehiculoEnum.CARRO);
		Vehiculo vehiculoConsultado = vehiculoRepository.buscarVehiculoPorPlaca("PQT29G");
		SalidaParqueadero salidaParqueadero = new SalidaParqueaderoTestDataBuilder()
				.conVehiculoSalida(vehiculoConsultado).build();
		int size = 1;

		// act
		salidaParqueaderoRepository.agregar(salidaParqueadero);
		// assert
		List<SalidaParqueadero> listaSalidaParqueadero = salidaParqueaderoRepository
				.obtenerSalidaVehiculosParqueadero();
		Assert.assertNotNull(listaSalidaParqueadero);
		Assert.assertEquals(size, listaSalidaParqueadero.size());
	}

	/**
	 * M�todo encargado de verificar que se obtengan las salidad de vehiculos del
	 * parqueadero
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
	@Test
	public void obtenerSalidaVehiculosParqueaderoTest() throws ParseException {
		// arrange
		datosAgregarSalidaParqueaderoTest("13/01/2019", TipoVehiculoEnum.CARRO);
		Vehiculo vehiculoConsultado = vehiculoRepository.buscarVehiculoPorPlaca("PQT29G");
		SalidaParqueadero salidaParqueadero = new SalidaParqueaderoTestDataBuilder()
				.conVehiculoSalida(vehiculoConsultado).build();
		salidaParqueaderoRepository.agregar(salidaParqueadero);
		int size = 1;
		// act
		List<SalidaParqueadero> listaSalidaParqueadero = salidaParqueaderoRepository
				.obtenerSalidaVehiculosParqueadero();
		// assert
		Assert.assertNotNull(listaSalidaParqueadero);
		Assert.assertEquals(size, listaSalidaParqueadero.size());

	}

	/**
	 * M�todo encargado de verificar el metodo calcularPrecioAPagar y que el precio
	 * sea cobrado por horas
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
	@Test
	public void calcularPrecioAPagarPorHoras() throws ParseException {
		// arrange
		Date fecha = new Date();
		Calendar fechaC = Calendar.getInstance();
		fechaC.setTime(fecha);
		fechaC.set(Calendar.HOUR, fechaC.get(Calendar.HOUR) - 3);
		fechaC.set(Calendar.MINUTE, fechaC.get(Calendar.MINUTE) - 20);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conFechaIngreso(fechaC.getTime()).build();
		vehiculoRepository.agregar(vehiculo);
		// act
		BigDecimal precioPagado = salidaParqueaderoRepository.calcularPrecioAPagar(vehiculo,
				SalidaParqueaderoPersistenciaRepository.VALOR_HORA_MOTO,
				SalidaParqueaderoPersistenciaRepository.VALOR_DIA_MOTO);
		// assert
		Assert.assertNotNull(precioPagado);
		Assert.assertEquals(new BigDecimal(4000), precioPagado);
	}

	/**
	 * M�todo encargado de verificar el metodo calcularPrecioAPagar cuando es menor
	 * a una hora
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
	@Test
	public void calcularPrecioAPagarUnaHora() throws ParseException {
		// arrange
		Date fecha = new Date();
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conFechaIngreso(fecha).build();
		vehiculoRepository.agregar(vehiculo);
		// act
		BigDecimal precioPagado = salidaParqueaderoRepository.calcularPrecioAPagar(vehiculo,
				SalidaParqueaderoPersistenciaRepository.VALOR_HORA_MOTO,
				SalidaParqueaderoPersistenciaRepository.VALOR_DIA_MOTO);
		// assert
		Assert.assertNotNull(precioPagado);
		Assert.assertEquals(new BigDecimal(2500), precioPagado);
	}

	/**
	 * M�todo encargado de verificar el metodo calcularPrecioAPagar y que el precio
	 * sea cobrado por horas
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
	@Test
	public void calcularPrecioAPagarPorDia() throws ParseException {
		// arrange
		datosCalcularPrecioApagar(0, 10);
		Vehiculo vehiculo = vehiculoRepository.buscarVehiculoPorPlaca("PQT29G");
		// act
		BigDecimal precioPagado = salidaParqueaderoRepository.calcularPrecioAPagar(vehiculo,
				SalidaParqueaderoPersistenciaRepository.VALOR_HORA_CARRO,
				SalidaParqueaderoPersistenciaRepository.VALOR_DIA_CARRO);
		// assert
		Assert.assertNotNull(precioPagado);
		Assert.assertEquals(new BigDecimal(8000), precioPagado);
	}

	/**
	 * M�todo encargado de verificar el metodo calcularPrecioAPagar y que el precio
	 * sea cobrado por por dias y horas
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
	@Test
	public void calcularPrecioAPagarPorMasDeUnDia() throws ParseException {
		// arrange
		datosCalcularPrecioApagar(1, 1);
		Vehiculo vehiculo = vehiculoRepository.buscarVehiculoPorPlaca("PQT29G");
		// act
		BigDecimal precioPagado = salidaParqueaderoRepository.calcularPrecioAPagar(vehiculo,
				SalidaParqueaderoPersistenciaRepository.VALOR_HORA_CARRO,
				SalidaParqueaderoPersistenciaRepository.VALOR_DIA_CARRO);
		// assert
		Assert.assertNotNull(precioPagado);
		Assert.assertEquals(new BigDecimal(10000), precioPagado);
	}

	/**
	 * M�todo encargado de verificar el metodo calcularPrecioAPagar y que el precio
	 * sea cobrado por horas
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
	@Test
	public void calcularPrecioAPagarPorMasDeUnDiaConHoras() throws ParseException {
		// arrange
		datosCalcularPrecioApagar(1, 10);
		Vehiculo vehiculo = vehiculoRepository.buscarVehiculoPorPlaca("PQT29G");
		// act
		BigDecimal precioPagado = salidaParqueaderoRepository.calcularPrecioAPagar(vehiculo,
				SalidaParqueaderoPersistenciaRepository.VALOR_HORA_CARRO,
				SalidaParqueaderoPersistenciaRepository.VALOR_DIA_CARRO);
		// assert
		Assert.assertNotNull(precioPagado);
		Assert.assertEquals(new BigDecimal(18000), precioPagado);
	}

	/**
	 * m�todo encargado de crear los datos para la prueba CalcularPrecioAPagar
	 * 
	 * @param             DiasRestar, numero de dias a restar
	 * @param horasRestar numero de horas a restar
	 */
	public void datosCalcularPrecioApagar(int DiasRestar, int horasRestar) {
		Date fecha = new Date();
		Calendar fechaC = Calendar.getInstance();
		fechaC.setTime(fecha);
		fechaC.set(Calendar.DAY_OF_MONTH, fechaC.get(Calendar.DAY_OF_MONTH) - DiasRestar);
		fechaC.set(Calendar.HOUR, fechaC.get(Calendar.HOUR) - horasRestar);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conFechaIngreso(fechaC.getTime())
				.conTipo(TipoVehiculoEnum.CARRO.name()).build();
		vehiculoRepository.agregar(vehiculo);
	}

	/**
	 * m�todo encargado de crear los datos para la creacion de una salida de un
	 * vehiculo del parqueadero
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
	private void datosAgregarSalidaParqueaderoTest(String fecha, TipoVehiculoEnum tipo) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaDate = formatter.parse(fecha);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conFechaIngreso(fechaDate).conTipo(tipo.name()).build();
		vehiculoRepository.agregar(vehiculo);
	}

	/**
	 * m�todo encargado de finalizar la conexion para las pruebas unitarias
	 */
	@After
	public void tearDown() {
		sistemaDePersistencia.terminar();
	}
}