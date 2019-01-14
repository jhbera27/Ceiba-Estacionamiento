package com.ceiba.estacionamiento.unitaria;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.ceiba.establecimiento.enums.TipoVehiculoEnum;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.dominio.excepcion.VehiculoException;
import com.ceiba.estacionamiento.dominio.repositorio.VehiculoRepository;
import com.ceiba.estacionamiento.persistencia.repositorio.VehiculoPersistenciaRepository;
import com.ceiba.estacionamiento.sistema.SistemaDePersistencia;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

/**
 * clase que permite realizar las pruebas unitarias para la clase
 * VehiculoPersistenciaRepository
 * 
 * @author jhon.bedoya
 *
 */
public class VehiculoPersistenciaRepositoryTest {
	/**
	 * instanciación del sistema de persistencia para las pruebas unitarias
	 */
	private SistemaDePersistencia sistemaDePersistencia;
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
		vehiculoRepository = sistemaDePersistencia.obtenerVehiculoRepository();
		sistemaDePersistencia.iniciar();
	}

	/**
	 * Método encargado de verificar que se agrege correctamente un vehiculo
	 * 
	 * RESULTADO ESPERADO: que se persista correctamente el nuevo vehiculo en el
	 * parqueadero
	 *
	 */
	@Test
	public void agregarVehiculoExitoTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		VehiculoRepository vehiculoRepositoryMock = mock(VehiculoRepository.class);
		when(vehiculoRepositoryMock.buscarVehiculoPorPlaca(vehiculo.getPlaca())).thenReturn(null);
		// act
		vehiculoRepository.agregar(vehiculo);
		// assert
		Assert.assertNotNull(vehiculoRepository.buscarVehiculoPorPlaca("PQT29G"));
	}

	/**
	 * Método encargado de verificar que se genere un error de cupo al intentar
	 * ingresar una vehiculo tipo moto al parqueadero
	 * 
	 * RESULTADO ESPERADO: que se genere una excepcion al intentar ingresar un
	 * vehiculo porque el parqueadero no tiene cupo para una moto
	 *
	 */
	@Test
	public void agregarMotoSinCupoTest() {
		// arrange
		List<Vehiculo> listaVehiculos = datosAgregarMotoSinCupoTest();
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PTT26H").build();
		VehiculoRepository vehiculoRepositoryMock = mock(VehiculoRepository.class);
		when(vehiculoRepositoryMock.obtenerVehiculos()).thenReturn(listaVehiculos);
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
	 * RESULTADO ESPERADO: que se genere una excepcion al intentar ingresar un
	 * vehiculo porque el parqueadero no tiene cupo para un carro
	 *
	 */
	@Test
	public void agregarCarroSinCupoTest() {
		// arrange
		List<Vehiculo> litaVehiculos = datosAgregarCarroSinCupoTest();
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PII21k").conTipo(TipoVehiculoEnum.CARRO)
				.build();
		VehiculoRepository vehiculoRepositoryMock = mock(VehiculoRepository.class);
		when(vehiculoRepositoryMock.obtenerVehiculos()).thenReturn(litaVehiculos);
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
		VehiculoRepository vehiculoRepositoryMock = mock(VehiculoRepository.class);
		when(vehiculoRepositoryMock.buscarVehiculoPorPlaca(vehiculoTest.getPlaca())).thenReturn(vehiculoTest);

		// act
		vehiculoRepository.agregar(vehiculoTest);

		// assert
		Vehiculo vehiculoConsultado = vehiculoRepository.buscarVehiculoPorPlaca("ZII21k");
		Assert.assertNotNull(vehiculoConsultado);
		Assert.assertTrue(vehiculoConsultado.getEstaParqueado());
		Assert.assertTrue(vehiculoConsultado.getFechaIngreso().equals(fecha));
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
	 * RESULTADO ESPERADO: que retorne todos los vehiculos que se encuentran en
	 * el parqueadero
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
	 * Método encargado de crear una lista de vehiculos tipo moto para verificar la
	 * prueba unitaria agregarMotoSinCupoTest()
	 * 
	 * @return lista de vehiculos tipo moto
	 */
	private List<Vehiculo> datosAgregarMotoSinCupoTest() {
		List<Vehiculo> listaVehiculos = new ArrayList<>();
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		listaVehiculos.add(vehiculo);
		Vehiculo vehiculo1 = new VehiculoTestDataBuilder().conPlaca("PTT29G").build();
		listaVehiculos.add(vehiculo1);
		Vehiculo vehiculo2 = new VehiculoTestDataBuilder().conPlaca("PTT27G").build();
		listaVehiculos.add(vehiculo2);
		Vehiculo vehiculo3 = new VehiculoTestDataBuilder().conPlaca("PTT29G").build();
		listaVehiculos.add(vehiculo3);
		Vehiculo vehiculo4 = new VehiculoTestDataBuilder().conPlaca("YTT29G").build();
		listaVehiculos.add(vehiculo4);
		Vehiculo vehiculo5 = new VehiculoTestDataBuilder().conPlaca("GTT29G").build();
		listaVehiculos.add(vehiculo5);
		Vehiculo vehiculo6 = new VehiculoTestDataBuilder().conPlaca("EBT29G").build();
		listaVehiculos.add(vehiculo6);
		Vehiculo vehiculo7 = new VehiculoTestDataBuilder().conPlaca("PTT29Y").build();
		listaVehiculos.add(vehiculo7);
		Vehiculo vehiculo8 = new VehiculoTestDataBuilder().conPlaca("PTT28G").build();
		listaVehiculos.add(vehiculo8);
		Vehiculo vehiculo9 = new VehiculoTestDataBuilder().conPlaca("PTT30G").build();
		listaVehiculos.add(vehiculo9);

		return listaVehiculos;
	}

	/**
	 * Método encargado de crear una lista de vehiculos tipo carro para verificar la
	 * prueba unitaria agregarCarroSinCupoTest()
	 * 
	 * @return lista de vehiculos tipo carro
	 */
	private List<Vehiculo> datosAgregarCarroSinCupoTest() {
		List<Vehiculo> listaVehiculos = new ArrayList<>();
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		listaVehiculos.add(vehiculo);
		Vehiculo vehiculo1 = new VehiculoTestDataBuilder().conPlaca("PTT29G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo1);
		Vehiculo vehiculo2 = new VehiculoTestDataBuilder().conPlaca("PTT27G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo2);
		Vehiculo vehiculo3 = new VehiculoTestDataBuilder().conPlaca("PTT29G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo3);
		Vehiculo vehiculo4 = new VehiculoTestDataBuilder().conPlaca("YTT29G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo4);
		Vehiculo vehiculo5 = new VehiculoTestDataBuilder().conPlaca("GTT29G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo5);
		Vehiculo vehiculo6 = new VehiculoTestDataBuilder().conPlaca("EBT29G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo6);
		Vehiculo vehiculo7 = new VehiculoTestDataBuilder().conPlaca("PTT29Y").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo7);
		Vehiculo vehiculo8 = new VehiculoTestDataBuilder().conPlaca("PTT28G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo8);
		Vehiculo vehiculo9 = new VehiculoTestDataBuilder().conPlaca("PTT30G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo9);
		Vehiculo vehiculo10 = new VehiculoTestDataBuilder().conPlaca("PTB29G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo10);
		Vehiculo vehiculo11 = new VehiculoTestDataBuilder().conPlaca("PCC27G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo11);
		Vehiculo vehiculo12 = new VehiculoTestDataBuilder().conPlaca("ZXT29G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo12);
		Vehiculo vehiculo13 = new VehiculoTestDataBuilder().conPlaca("MTT29G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo13);
		Vehiculo vehiculo14 = new VehiculoTestDataBuilder().conPlaca("GTT90G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo14);
		Vehiculo vehiculo15 = new VehiculoTestDataBuilder().conPlaca("EWT29G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo15);
		Vehiculo vehiculo16 = new VehiculoTestDataBuilder().conPlaca("PTT97Y").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo16);
		Vehiculo vehiculo17 = new VehiculoTestDataBuilder().conPlaca("QQQ28G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo17);
		Vehiculo vehiculo18 = new VehiculoTestDataBuilder().conPlaca("PPP30G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo18);
		Vehiculo vehiculo19 = new VehiculoTestDataBuilder().conPlaca("PII30G").conTipo(TipoVehiculoEnum.CARRO).build();
		listaVehiculos.add(vehiculo19);

		return listaVehiculos;
	}

	/**
	 * método encargado de finalizar la conexion para las pruebas unitarias
	 */
	@After
	public void tearDown() {
		sistemaDePersistencia.terminar();
	}

}
