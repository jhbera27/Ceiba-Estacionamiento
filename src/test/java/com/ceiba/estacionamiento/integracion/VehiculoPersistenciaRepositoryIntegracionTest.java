package com.ceiba.estacionamiento.integracion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * clase que permite realizar las pruebas de integracion para la clase
 * VehiculoPersistenciaRepository
 * 
 * @author jhon.bedoya
 *
 */
public class VehiculoPersistenciaRepositoryIntegracionTest {
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
		datosAgregarMotoSinCupoTest();
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PTT26H").build();
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
		datosAgregarCarroSinCupoTest();
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PII21k").conTipo(TipoVehiculoEnum.CARRO)
				.build();
		try {
			// act
			vehiculoRepository.agregar(vehiculoTest);
		} catch (VehiculoException e) {
			// assert
			Assert.assertEquals(VehiculoPersistenciaRepository.SIN_CUPO, e.getMessage());
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
		// act
		vehiculoRepository.agregar(vehiculoTest);

		// assert
		Vehiculo vehiculoConsultado = vehiculoRepository.buscarVehiculoPorPlaca("ZII21k");
		Assert.assertNotNull(vehiculoConsultado);
		Assert.assertTrue(vehiculoConsultado.getEstaParqueado());
		Assert.assertTrue(vehiculoConsultado.getFechaIngreso().equals(fecha));
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
		Vehiculo vehiculo3 = new VehiculoTestDataBuilder().conPlaca("PTT29G").build();
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
		Vehiculo vehiculo1 = new VehiculoTestDataBuilder().conPlaca("PTT29G").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo1);
		Vehiculo vehiculo2 = new VehiculoTestDataBuilder().conPlaca("PTT27G").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo2);
		Vehiculo vehiculo3 = new VehiculoTestDataBuilder().conPlaca("PTT29G").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo3);
		Vehiculo vehiculo4 = new VehiculoTestDataBuilder().conPlaca("YTT29G").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo4);
		Vehiculo vehiculo5 = new VehiculoTestDataBuilder().conPlaca("GTT29G").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo5);
		Vehiculo vehiculo6 = new VehiculoTestDataBuilder().conPlaca("EBT29G").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo6);
		Vehiculo vehiculo7 = new VehiculoTestDataBuilder().conPlaca("PTT29Y").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo7);
		Vehiculo vehiculo8 = new VehiculoTestDataBuilder().conPlaca("PTT28G").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo8);
		Vehiculo vehiculo9 = new VehiculoTestDataBuilder().conPlaca("PTT30G").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo9);
		Vehiculo vehiculo10 = new VehiculoTestDataBuilder().conPlaca("PTB29G").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo10);
		Vehiculo vehiculo11 = new VehiculoTestDataBuilder().conPlaca("PCC27G").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo11);
		Vehiculo vehiculo12 = new VehiculoTestDataBuilder().conPlaca("ZXT29G").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo12);
		Vehiculo vehiculo13 = new VehiculoTestDataBuilder().conPlaca("MTT29G").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo13);
		Vehiculo vehiculo14 = new VehiculoTestDataBuilder().conPlaca("GTT90G").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo14);
		Vehiculo vehiculo15 = new VehiculoTestDataBuilder().conPlaca("EWT29G").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo15);
		Vehiculo vehiculo16 = new VehiculoTestDataBuilder().conPlaca("PTT97Y").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo16);
		Vehiculo vehiculo17 = new VehiculoTestDataBuilder().conPlaca("QQQ28G").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo17);
		Vehiculo vehiculo18 = new VehiculoTestDataBuilder().conPlaca("PPP30G").conTipo(TipoVehiculoEnum.CARRO).build();
		vehiculoRepository.agregar(vehiculo18);
		Vehiculo vehiculo19 = new VehiculoTestDataBuilder().conPlaca("PII30G").conTipo(TipoVehiculoEnum.CARRO).build();
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
