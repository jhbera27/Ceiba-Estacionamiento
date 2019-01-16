package com.ceiba.estacionamiento.unitaria;

import static org.mockito.Mockito.when;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ceiba.establecimiento.enums.TipoVehiculoEnum;
import com.ceiba.estacinamiento.dominio.SalidaParqueadero;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.dominio.excepcion.VehiculoException;
import com.ceiba.estacionamiento.persistencia.repositorio.SalidaParqueaderoPersistenciaRepository;
import com.ceiba.estacionamiento.persistencia.repositorio.VehiculoPersistenciaRepository;
import com.ceiba.estacionamiento.sistema.SistemaDePersistencia;
import com.ceiba.estacionamiento.testdatabuilder.SalidaParqueaderoTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;
import com.ceiba.estacionamiento.utils.Utils;

/**
 * clase que permite realizar las pruebas unitarias para la clase
 * VehiculoPersistenciaRepository
 * 
 * @author jhon.bedoya
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( { Utils.class})
public class VehiculoPersistenciaRepositoryTest {
	/**
	 * instanciación del sistema de persistencia para las pruebas unitarias
	 */
	private SistemaDePersistencia sistemaDePersistencia;
	/**
	 * instanciación del repositorio de salida de vehiculos para las pruebas unitarias
	 */
	private SalidaParqueaderoPersistenciaRepository SalidaParqueaderoRepository;
	/**
	 * instanciación del repositorio de vehiculos para las pruebas unitarias
	 */
	private VehiculoPersistenciaRepository vehiculoRepository;

	/**
	 * método encargado de inicializar el contexto de pertistencia para las pruebas
	 * unitarias
	 */
	@Before
	public void setUp() {
		sistemaDePersistencia = new SistemaDePersistencia();
		vehiculoRepository = (VehiculoPersistenciaRepository) sistemaDePersistencia.obtenerVehiculoRepository();
		SalidaParqueaderoRepository = (SalidaParqueaderoPersistenciaRepository) sistemaDePersistencia
				.obtenerSalidaParqueaderoRepository();
		sistemaDePersistencia.iniciar();
	}

	/**
	 * Método encargado de verificar que se agrege correctamente un vehiculo
	 *
	 */
	@Test
	public void agregarVehiculoExitoTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		PowerMockito.mockStatic(Utils.class);
		when(Utils.verificarPlaca(vehiculo.getPlaca())).thenReturn(Boolean.TRUE);
		// act
		vehiculoRepository.agregar(vehiculo);
		// assert
		Assert.assertNotNull(vehiculoRepository.buscarVehiculoPorPlaca("PQT29G"));
	}

	/**
	 * Método encargado de verificar que se genere un error de cupo al intentar
	 * ingresar una vehiculo tipo moto al parqueadero
	 *
	 */
	@Test
	public void agregarMotoSinCupoTest() {
		// arrange
		datosAgregarMotoSinCupoTest();
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PTT26H").build();
		PowerMockito.mockStatic(Utils.class);
		when(Utils.verificarPlaca(vehiculoTest.getPlaca())).thenReturn(Boolean.TRUE);
		try {
			// act
			vehiculoRepository.agregar(vehiculoTest);
		} catch (VehiculoException e) {
			// assert
			Assert.assertEquals(VehiculoPersistenciaRepository.SIN_CUPO, e.getMessage());
		}

	}

	/**
	 * Método encargado de verificar que se genere un error de cupo al intentar
	 * ingresar una vehiculo tipo carro al parqueadero
	 * 
	 */
	@Test
	public void agregarCarroSinCupoTest() {
		// arrange
		datosAgregarCarroSinCupoTest();
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PII21k").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		PowerMockito.mockStatic(Utils.class);
		when(Utils.verificarPlaca(vehiculoTest.getPlaca())).thenReturn(Boolean.TRUE);
		try {
			// act
			vehiculoRepository.agregar(vehiculoTest);
		} catch (VehiculoException e) {
			// assert
			Assert.assertEquals(VehiculoPersistenciaRepository.SIN_CUPO, e.getMessage());
		}
	}

	/**
	 * Método encargado de verificar que se genere un error al intentar ingresar un
	 * vehiculo con placa no autorizada
	 * 
	 * RESULTADO ESPERADO: que se genere una excepcion al intentar ingresar un
	 * vehiculo porque la placa del vehiculo empieza por A y no es un dia valido
	 * para esa placa
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
	@Test
	public void verificarPlacaNoPermitidaTest() throws ParseException {
		// arrange
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateInString = "11/01/2019";
		Date fecha = formatter.parse(dateInString);
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("AII21k").conFechaIngreso(fecha).build();
		PowerMockito.mockStatic(Utils.class);
		when(Utils.verificarPlaca(vehiculoTest.getPlaca())).thenReturn(Boolean.FALSE);
		try {
			// act
			vehiculoRepository.agregar(vehiculoTest);
		} catch (VehiculoException e) {
			// assert
			Assert.assertEquals(VehiculoPersistenciaRepository.VEHICULO_NO_AUTORIZADO, e.getMessage());
		}
	}

	/**
	 * Método encargado de verificar que se actualice un vehiculo que se encontraba
	 * creado pero en estado no parqueado con la nueva informacion de entrada al
	 * parqueadero
	 * 
	 * RESULTADO ESPERADO: que se actualice correctamente un vehiculo que ya se
	 * encontraba creado y que se desea actualizar los datos del nuevo ingreso al
	 * parqueadero
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
	@Test
	public void actualizarVehiculoTest() throws ParseException {
		// arrange
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateInString = "11/01/2019";
		Date fecha = formatter.parse(dateInString);
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("ZII21k").conFechaIngreso(fecha)
				.conEstaParqueado(Boolean.FALSE).build();
		vehiculoRepository.agregar(vehiculoTest);
		Vehiculo vehiculoConsultadoArrange = vehiculoRepository.buscarVehiculoPorPlaca("ZII21k");
		SalidaParqueadero salidaParqueadero = new SalidaParqueaderoTestDataBuilder()
				.conVehiculoSalida(vehiculoConsultadoArrange).build();
		SalidaParqueaderoRepository.agregar(salidaParqueadero);
		PowerMockito.mockStatic(Utils.class);
		when(Utils.verificarPlaca(vehiculoTest.getPlaca())).thenReturn(Boolean.TRUE);
		// act
		vehiculoRepository.agregar(vehiculoTest);
		// assert
		Vehiculo vehiculoConsultado = vehiculoRepository.buscarVehiculoPorPlaca("ZII21k");
		Assert.assertNotNull(vehiculoConsultado);
		Assert.assertTrue(vehiculoConsultado.getEstaParqueado());
	}

	/**
	 * Método encargado de verificar que se genere se consulte correctamente un
	 * vehiculo dado su placa
	 * 
	 * RESULTADO ESPERADO: que retorne el vehiculo asociado a la placa que se
	 * ingresa como parametro
	 *
	 */
	@Test
	public void buscarVehiculoPorPlacaExitoTest() {
		// arrange
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("LTT26H").build();
		vehiculoRepository.agregar(vehiculoTest);
		// act
		Vehiculo vehiculoConsultado = vehiculoRepository.buscarVehiculoPorPlaca("LTT26H");
		// assert
		Assert.assertNotNull(vehiculoConsultado);
	}

	/**
	 * Método encargado de verificar que se consulte todos los vehiculos en el
	 * parqueadero
	 * 
	 * RESULTADO ESPERADO: que retorne todos los vehiculos que se encuentran en el
	 * parqueadero
	 *
	 */
	@Test
	public void buscarVehiculoPorPlacaNullTest() {
		// arrange
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("LTT26H").build();
		vehiculoRepository.agregar(vehiculoTest);
		// act
		Vehiculo vehiculoConsultado = vehiculoRepository.buscarVehiculoPorPlaca("LZT50H");
		// assert
		Assert.assertNull(vehiculoConsultado);
	}

	/**
	 * Método encargado de verificar que se genere se consulte correctamente un
	 * vehiculo dado su placa
	 * 
	 * RESULTADO ESPERADO: que retorne null dado que no existe el vehiculo asociado
	 * a esa placa
	 *
	 */
	@Test
	public void obtenerVehiculosTest() {
		// arrange
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("LTT26H").build();
		vehiculoRepository.agregar(vehiculoTest);
		Vehiculo vehiculoTest2 = new VehiculoTestDataBuilder().conPlaca("LTP34H").build();
		vehiculoRepository.agregar(vehiculoTest2);
		int size = 2;
		// act
		List<Vehiculo> listaVehiculos = vehiculoRepository.obtenerVehiculos();
		// assert
		Assert.assertNotNull(listaVehiculos);
		Assert.assertEquals(size, listaVehiculos.size());
	}

	/**
	 * Método encargado depersistir un conjunto de vehiculos tipo moto para
	 * verificar la prueba unitaria agregarMotoSinCupoTest()
	 * 
	 */
	private void datosAgregarMotoSinCupoTest() {
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		vehiculoRepository.agregar(vehiculo);
		Vehiculo vehiculo1 = new VehiculoTestDataBuilder().conPlaca("PTT29G").build();
		vehiculoRepository.agregar(vehiculo1);
		Vehiculo vehiculo2 = new VehiculoTestDataBuilder().conPlaca("PTT27G").build();
		vehiculoRepository.agregar(vehiculo2);
		Vehiculo vehiculo3 = new VehiculoTestDataBuilder().conPlaca("PTT29W").build();
		vehiculoRepository.agregar(vehiculo3);
		Vehiculo vehiculo4 = new VehiculoTestDataBuilder().conPlaca("YTT29G").build();
		vehiculoRepository.agregar(vehiculo4);
		Vehiculo vehiculo5 = new VehiculoTestDataBuilder().conPlaca("GTT29G").build();
		vehiculoRepository.agregar(vehiculo5);
		Vehiculo vehiculo6 = new VehiculoTestDataBuilder().conPlaca("EBT29G").build();
		vehiculoRepository.agregar(vehiculo6);
		Vehiculo vehiculo7 = new VehiculoTestDataBuilder().conPlaca("PTT29Y").build();
		vehiculoRepository.agregar(vehiculo7);
		Vehiculo vehiculo8 = new VehiculoTestDataBuilder().conPlaca("PTT28G").build();
		vehiculoRepository.agregar(vehiculo8);
		Vehiculo vehiculo9 = new VehiculoTestDataBuilder().conPlaca("PTT30G").build();
		vehiculoRepository.agregar(vehiculo9);
	}

	/**
	 * Método encargado de persistir un conjunto de vehiculos tipo carro para
	 * verificar la prueba unitaria agregarCarroSinCupoTest()
	 * 
	 */
	private void datosAgregarCarroSinCupoTest() {
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		vehiculoRepository.agregar(vehiculo);
		Vehiculo vehiculo1 = new VehiculoTestDataBuilder().conPlaca("PTT29G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo1);
		Vehiculo vehiculo2 = new VehiculoTestDataBuilder().conPlaca("PTT27G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo2);
		Vehiculo vehiculo3 = new VehiculoTestDataBuilder().conPlaca("PTT45Z").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo3);
		Vehiculo vehiculo4 = new VehiculoTestDataBuilder().conPlaca("YTT46G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo4);
		Vehiculo vehiculo5 = new VehiculoTestDataBuilder().conPlaca("GTT47G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo5);
		Vehiculo vehiculo6 = new VehiculoTestDataBuilder().conPlaca("EBT48G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo6);
		Vehiculo vehiculo7 = new VehiculoTestDataBuilder().conPlaca("PTT49Y").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo7);
		Vehiculo vehiculo8 = new VehiculoTestDataBuilder().conPlaca("PTT50G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo8);
		Vehiculo vehiculo9 = new VehiculoTestDataBuilder().conPlaca("PTT51G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo9);
		Vehiculo vehiculo10 = new VehiculoTestDataBuilder().conPlaca("PTB52G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo10);
		Vehiculo vehiculo11 = new VehiculoTestDataBuilder().conPlaca("PCC53G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo11);
		Vehiculo vehiculo12 = new VehiculoTestDataBuilder().conPlaca("ZXT54G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo12);
		Vehiculo vehiculo13 = new VehiculoTestDataBuilder().conPlaca("MTT55G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo13);
		Vehiculo vehiculo14 = new VehiculoTestDataBuilder().conPlaca("GTT56G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo14);
		Vehiculo vehiculo15 = new VehiculoTestDataBuilder().conPlaca("EWT57G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo15);
		Vehiculo vehiculo16 = new VehiculoTestDataBuilder().conPlaca("PTT58Y").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo16);
		Vehiculo vehiculo17 = new VehiculoTestDataBuilder().conPlaca("QQQ59G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo17);
		Vehiculo vehiculo18 = new VehiculoTestDataBuilder().conPlaca("PPP60G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo18);
		Vehiculo vehiculo19 = new VehiculoTestDataBuilder().conPlaca("PII61G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoRepository.agregar(vehiculo19);

	}

	/**
	 * método encargado de finalizar la conexion para las pruebas unitarias
	 */
	@After
	public void tearDown() {
		sistemaDePersistencia.terminar();
	}

}
