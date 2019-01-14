package com.ceiba.estacionamiento.integracion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.ceiba.estacionamiento.sistema.SistemaDePersistencia;
import com.ceiba.estacionamiento.testdatabuilder.SalidaParqueaderoTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

/**
 * clase que permite realizar las pruebas de integracion para la clase
 * SalidaParqueaderoPersistenciaRepository
 * 
 * @author jhon.bedoya
 *
 */
public class SalidaParqueaderoPersistenciaRepositoryIntegracion {
	/**
	 * instanciación del sistema de persistencia para las pruebas unitarias
	 */
	private SistemaDePersistencia sistemaDePersistencia;
	/**
	 * instanciación del repositorio de salida de vehiculos del parqueaderos para
	 * las pruebas unitarias
	 */
	private SalidaParqueaderoRepository salidaParqueaderoRepository;
	/**
	 * instanciación del repositorio de vehiculos para las pruebas unitarias
	 */
	private VehiculoRepository vehiculoRepository;


	/**
	 * método encargado de inicializar el contexto de pertistencia para las pruebas
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
	 * Método encargado de verificar que se almacene correctamente una salida de un
	 * vehiculo tipo moto del parqueadero
	 * 
	 * RESULTADO ESPERADO: que se cree correctamente el registro de la salida de un
	 * vehiculo del parqueadero
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
	 * método encargado de crear los datos para la creacion de una salida de un
	 * vehiculo del parqueadero
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
	private void datosAgregarSalidaParqueaderoTest(String fecha, TipoVehiculoEnum tipo) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaDate = formatter.parse(fecha);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conFechaIngreso(fechaDate).conTipo(tipo).build();
		vehiculoRepository.agregar(vehiculo);
	}
	
	/**
	 * método encargado de finalizar la conexion para las pruebas unitarias
	 */
	@After
	public void tearDown() {
		sistemaDePersistencia.terminar();
	}
}
