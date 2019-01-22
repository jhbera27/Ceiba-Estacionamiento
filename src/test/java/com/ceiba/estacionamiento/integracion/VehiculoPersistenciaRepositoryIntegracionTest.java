package com.ceiba.estacionamiento.integracion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.ceiba.estacinamiento.dominio.SalidaParqueadero;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.dominio.excepcion.VehiculoException;
import com.ceiba.estacionamiento.testdatabuilder.SalidaParqueaderoTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

/**
 * clase que permite realizar las pruebas de integracion para la clase
 * VehiculoPersistenciaRepository
 * 
 * @author jhon.bedoya
 *
 */
public class VehiculoPersistenciaRepositoryIntegracionTest {
//
//	/**
//	 * instanciación del sistema de persistencia para las pruebas unitarias
//	 */
//	private SistemaDePersistencia sistemaDePersistencia;
//	/**
//	 * instanciación del repositorio de salida de vehiculos para las pruebas
//	 * unitarias
//	 */
//	private SalidaParqueaderoPersistenciaRepository SalidaParqueaderoRepository;
//	/**
//	 * instanciación del repositorio de vehiculos para las pruebas unitarias
//	 */
//	private VehiculoPersistenciaRepository vehiculoRepository;
//
//	/**
//	 * método encargado de inicializar el contexto de pertistencia para las pruebas
//	 * unitarias
//	 */
//	@Before
//	public void setUp() {
//		sistemaDePersistencia = new SistemaDePersistencia();
//		vehiculoRepository = (VehiculoPersistenciaRepository) sistemaDePersistencia.obtenerVehiculoRepository();
//		SalidaParqueaderoRepository = (SalidaParqueaderoPersistenciaRepository) sistemaDePersistencia
//				.obtenerSalidaParqueaderoRepository();
//		sistemaDePersistencia.iniciar();
//	}
//
//	/**
//	 * Método encargado de verificar que se agrege correctamente un vehiculo
//	 *
//	 */
//	@Test
//	public void agregarVehiculoExitoTest() {
//		// arrange
//		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
//		// act
//		vehiculoRepository.agregar(vehiculo);
//		// assert
//		Assert.assertNotNull(vehiculoRepository.buscarVehiculoPorPlaca("PQT29G"));
//	}
//
//	/**
//	 * Método encargado de verificar que se genere un error al intentar ingresar un
//	 * vehiculo con placa no autorizada
//	 * 
//	 * @throws ParseException, excepcion generada al intentar convertir una fecha
//	 */
//	@Test
//	public void verificarPlacaNoPermitidaTest() throws ParseException {
//		// arrange
//		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//		String dateInString = "11/01/2019";
//		Date fecha = formatter.parse(dateInString);
//		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("AII21k").conFechaIngreso(fecha).build();
//		try {
//			// act
//			vehiculoRepository.agregar(vehiculoTest);
//		} catch (VehiculoException e) {
//			// assert
//			Assert.assertEquals(VehiculoPersistenciaRepository.VEHICULO_NO_AUTORIZADO, e.getMessage());
//		}
//	}
//
//	/**
//	 * Método encargado de verificar que se actualice un vehiculo que se encontraba
//	 * creado pero en estado no parqueado con la nueva informacion de entrada al
//	 * parqueadero
//	 * 
//	 * @throws ParseException, excepcion generada al intentar convertir una fecha
//	 */
//	@Test
//	public void actualizarVehiculoTest() throws ParseException {
//		// arrange
//		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//		String dateInString = "11/01/2019";
//		Date fecha = formatter.parse(dateInString);
//		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("ZII21k").conFechaIngreso(fecha)
//				.conEstaParqueado(Boolean.FALSE).build();
//		vehiculoRepository.agregar(vehiculoTest);
//		Vehiculo vehiculoConsultadoArrange = vehiculoRepository.buscarVehiculoPorPlaca("ZII21k");
//		SalidaParqueadero salidaParqueadero = new SalidaParqueaderoTestDataBuilder()
//				.conVehiculoSalida(vehiculoConsultadoArrange).build();
//		SalidaParqueaderoRepository.agregar(salidaParqueadero);
//		// act
//		vehiculoRepository.agregar(vehiculoTest);
//		// assert
//		Vehiculo vehiculoConsultado = vehiculoRepository.buscarVehiculoPorPlaca("ZII21k");
//		Assert.assertNotNull(vehiculoConsultado);
//		Assert.assertTrue(vehiculoConsultado.getEstaParqueado());
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
