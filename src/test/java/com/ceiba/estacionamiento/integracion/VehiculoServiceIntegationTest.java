package com.ceiba.estacionamiento.integracion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.ceiba.estacionamiento.dominio.excepcion.VehiculoException;
import com.ceiba.estacionamiento.estacionamiento.EstacionamientoApplication;
import com.ceiba.estacionamiento.persistencia.repositorio.SalidaParqueaderoRepository;
import com.ceiba.estacionamiento.persistencia.repositorio.VehiculoRepository;
import com.ceiba.estacionamiento.persistencia.service.impl.SalidaParqueaderoServiceImpl;
import com.ceiba.estacionamiento.persistencia.service.impl.VehiculoServiceImpl;
import com.ceiba.estacionamiento.testdatabuilder.SalidaParqueaderoTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EstacionamientoApplication.class)
public class VehiculoServiceIntegationTest {

	@Autowired
	private VehiculoRepository vehiculoRepository;

	private VehiculoServiceImpl vehiculoService;

	@Autowired
	private SalidaParqueaderoRepository salidaParqueaderoRepository;

	private SalidaParqueaderoServiceImpl SalidaParqueaderoService;

	/**
	 * método encargado de inicializar el contexto de pertistencia para las pruebas
	 * unitarias
	 */
	@Before
	public void setUp() {
		vehiculoService = new VehiculoServiceImpl(vehiculoRepository);
		SalidaParqueaderoService = new SalidaParqueaderoServiceImpl(salidaParqueaderoRepository, vehiculoRepository);
	}

	/**
	 * Método encargado de verificar que se agrege correctamente un vehiculo
	 *
	 */
//	@Test
//	@Transactional
//	@Rollback(true)
	public void agregarVehiculoExitoTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		// act
		vehiculoService.agregar(vehiculo);
		// assert
		Assert.assertNotNull(vehiculoService.buscarVehiculoPorPlaca(vehiculo.getPlaca()));
	}

	/**
	 * Método encargado de verificar que se genere un error de cupo al intentar
	 * ingresar una vehiculo tipo moto al parqueadero
	 *
	 */
//	@Test
//	@Transactional
//	@Rollback(true)
	public void agregarMotoSinCupoTest() {
		// arrange
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PVV26H").build();
		datosAgregarMotoSinCupoTest();
		try {
			// act
			vehiculoService.agregar(vehiculoTest);
		} catch (VehiculoException e) {
			// assert
			Assert.assertEquals(VehiculoServiceImpl.SIN_CUPO, e.getMessage());
		}
	}

	/**
	 * Método encargado de verificar que se genere un error de cupo al intentar
	 * ingresar una vehiculo tipo carro al parqueadero
	 * 
	 */
//	@Test
//	@Transactional
//	@Rollback(true)
	public void agregarCarroSinCupoTest() {
		// arrange
		datosAgregarCarroSinCupoTest();
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PII21k").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		try {
			// act
			vehiculoService.agregar(vehiculoTest);
		} catch (VehiculoException e) {
			// assert
			Assert.assertEquals(VehiculoServiceImpl.SIN_CUPO, e.getMessage());
		}
	}

	/**
	 * Método encargado de verificar que se genere un error al intentar ingresar un
	 * vehiculo con placa no autorizada
	 * 
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
//	@Test
//	@Transactional
//	@Rollback(true)
	public void verificarPlacaNoPermitidaTest() throws ParseException {
		// arrange
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateInString = "11/01/2019";
		Date fecha = formatter.parse(dateInString);
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("AII21k").conFechaIngreso(fecha).build();
		try {
			// act
			vehiculoService.agregar(vehiculoTest);
		} catch (VehiculoException e) {
			// assert
			Assert.assertEquals(VehiculoServiceImpl.VEHICULO_NO_AUTORIZADO, e.getMessage());
		}
	}

	/**
	 * Método encargado de verificar que se actualice un vehiculo que se encontraba
	 * creado pero en estado no parqueado con la nueva informacion de entrada al
	 * parqueadero
	 * 
	 * 
	 * @throws ParseException, excepcion generada al intentar convertir una fecha
	 */
//	@Test
//	@Transactional
//	@Rollback(true)
	public void actualizarVehiculoTest() {
		// arrange
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("ZII21k").conEstaParqueado(Boolean.FALSE)
				.build();
		datosActualizarVehiculoTest();
		// act
		vehiculoService.agregar(vehiculoTest);
		Vehiculo vehiculoEncontrado = vehiculoService.buscarVehiculoPorPlaca(vehiculoTest.getPlaca());
		// assert
		Assert.assertNotNull(vehiculoEncontrado);
		Assert.assertTrue(vehiculoEncontrado.getEstaParqueado());

	}

	/**
	 * Método encargado de verificar que se genere se consulte correctamente un
	 * vehiculo dado su placa
	 *
	 */
//	@Test
//	@Transactional
//	@Rollback(true)
	public void buscarVehiculoPorPlacaExitoTest() {
		// arrange
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("LTT26H").build();
		vehiculoService.agregar(vehiculoTest);
		// act
		Vehiculo vehiculoConsultado = vehiculoService.buscarVehiculoPorPlaca(vehiculoTest.getPlaca());
		// assert
		Assert.assertNotNull(vehiculoConsultado);
	}

	/**
	 * Método encargado de verificar que se consulte todos los vehiculos en el
	 * parqueadero
	 * 
	 *
	 */
//	@Test
//	@Transactional
//	@Rollback(true)
	public void buscarVehiculoPorPlacaNullTest() {
		// arrange
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("LTT26H").build();
		vehiculoService.agregar(vehiculoTest);
		// act
		Vehiculo vehiculoConsultado = vehiculoService.buscarVehiculoPorPlaca("LZT50H");
		// assert
		Assert.assertNull(vehiculoConsultado);
	}

	/**
	 * Método encargado de verificar que se genere se consulte correctamente un
	 * vehiculo dado su placa
	 * 
	 *
	 */
//	@Test
//	@Transactional
//	@Rollback(true)
	public void obtenerVehiculosTest() {
		// arrange
		datosObtenerVehiculosTest();
		// act
		List<Vehiculo> listaVehiculos = vehiculoService.obtenerVehiculos();
		// assert
		Assert.assertNotNull(listaVehiculos);
		Assert.assertEquals(2, listaVehiculos.size());
	}

	private void datosActualizarVehiculoTest() {
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("ZII21k").conEstaParqueado(Boolean.FALSE)
				.build();
		vehiculoService.agregar(vehiculoTest);
		Vehiculo vehiculoPersistido = vehiculoService.buscarVehiculoPorPlaca(vehiculoTest.getPlaca());
		SalidaParqueadero salidaParqueadero = new SalidaParqueaderoTestDataBuilder()
				.conVehiculoSalida(vehiculoPersistido).build();
		SalidaParqueaderoService.agregar(salidaParqueadero);
	}

	/**
	 * método en cargado de crear una lista de vehiculosEntity para la prueba
	 * ObtenerVehiculosTest
	 * 
	 * @return lista de vehiculos tipo entity
	 */
	private void datosObtenerVehiculosTest() {
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("LAD26H").build();
		vehiculoService.agregar(vehiculo);
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("LQW26H").build();
		vehiculoService.agregar(vehiculoTest);
	}

	/**
	 * Método encargado de crear una lista de vehiculos tipo moto para verificar la
	 * prueba unitaria agregarMotoSinCupoTest()
	 * 
	 * @return, lista de vehiculos tipo moto
	 */
	private void datosAgregarMotoSinCupoTest() {
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		vehiculoService.agregar(vehiculo);
		Vehiculo vehiculo1 = new VehiculoTestDataBuilder().conPlaca("PTT29G").build();
		vehiculoService.agregar(vehiculo1);
		Vehiculo vehiculo2 = new VehiculoTestDataBuilder().conPlaca("PTT27G").build();
		vehiculoService.agregar(vehiculo2);
		Vehiculo vehiculo3 = new VehiculoTestDataBuilder().conPlaca("PTT29W").build();
		vehiculoService.agregar(vehiculo3);
		Vehiculo vehiculo4 = new VehiculoTestDataBuilder().conPlaca("YTT29G").build();
		vehiculoService.agregar(vehiculo4);
		Vehiculo vehiculo5 = new VehiculoTestDataBuilder().conPlaca("GTT29G").build();
		vehiculoService.agregar(vehiculo5);
		Vehiculo vehiculo6 = new VehiculoTestDataBuilder().conPlaca("EBT29G").build();
		vehiculoService.agregar(vehiculo6);
		Vehiculo vehiculo7 = new VehiculoTestDataBuilder().conPlaca("PTT29Y").build();
		vehiculoService.agregar(vehiculo7);
		Vehiculo vehiculo8 = new VehiculoTestDataBuilder().conPlaca("PTT28G").build();
		vehiculoService.agregar(vehiculo8);
		Vehiculo vehiculo9 = new VehiculoTestDataBuilder().conPlaca("PTT30G").build();
		vehiculoService.agregar(vehiculo9);
	}

	/**
	 * Método encargado de persistir un conjunto de vehiculos tipo carro para
	 * verificar la prueba unitaria agregarCarroSinCupoTest()
	 * 
	 * @return, lista de vehiculos tipo carro
	 */
	private void datosAgregarCarroSinCupoTest() {
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		vehiculoService.agregar(vehiculo);
		Vehiculo vehiculo1 = new VehiculoTestDataBuilder().conPlaca("PTT29G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo1);
		Vehiculo vehiculo2 = new VehiculoTestDataBuilder().conPlaca("PTT27G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo2);
		Vehiculo vehiculo3 = new VehiculoTestDataBuilder().conPlaca("PTT45Z").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo3);
		Vehiculo vehiculo4 = new VehiculoTestDataBuilder().conPlaca("YTT46G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo4);
		Vehiculo vehiculo5 = new VehiculoTestDataBuilder().conPlaca("GTT47G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo5);
		Vehiculo vehiculo6 = new VehiculoTestDataBuilder().conPlaca("EBT48G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo6);
		Vehiculo vehiculo7 = new VehiculoTestDataBuilder().conPlaca("PTT49Y").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo7);
		Vehiculo vehiculo8 = new VehiculoTestDataBuilder().conPlaca("PTT50G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo8);
		Vehiculo vehiculo9 = new VehiculoTestDataBuilder().conPlaca("PTT51G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo9);
		Vehiculo vehiculo10 = new VehiculoTestDataBuilder().conPlaca("PTB52G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo10);
		Vehiculo vehiculo11 = new VehiculoTestDataBuilder().conPlaca("PCC53G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo11);
		Vehiculo vehiculo12 = new VehiculoTestDataBuilder().conPlaca("ZXT54G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo12);
		Vehiculo vehiculo13 = new VehiculoTestDataBuilder().conPlaca("MTT55G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo13);
		Vehiculo vehiculo14 = new VehiculoTestDataBuilder().conPlaca("GTT56G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo14);
		Vehiculo vehiculo15 = new VehiculoTestDataBuilder().conPlaca("EWT57G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo15);
		Vehiculo vehiculo16 = new VehiculoTestDataBuilder().conPlaca("PTT58Y").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo16);
		Vehiculo vehiculo17 = new VehiculoTestDataBuilder().conPlaca("QQQ59G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo17);
		Vehiculo vehiculo18 = new VehiculoTestDataBuilder().conPlaca("PPP60G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo18);
		Vehiculo vehiculo19 = new VehiculoTestDataBuilder().conPlaca("PII61G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo19);
		Vehiculo vehiculo20 = new VehiculoTestDataBuilder().conPlaca("PII89X").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		vehiculoService.agregar(vehiculo20);
	}

}
