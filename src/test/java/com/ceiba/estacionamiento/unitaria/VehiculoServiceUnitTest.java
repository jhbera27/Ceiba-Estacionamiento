package com.ceiba.estacionamiento.unitaria;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.establecimiento.enums.TipoVehiculoEnum;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.dominio.excepcion.VehiculoException;
import com.ceiba.estacionamiento.persistencia.builder.VehiculoBuilder;
import com.ceiba.estacionamiento.persistencia.entity.VehiculoEntity;
import com.ceiba.estacionamiento.persistencia.repositorio.VehiculoRepository;
import com.ceiba.estacionamiento.persistencia.service.impl.VehiculoServiceImpl;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;


public class VehiculoServiceUnitTest {

	private VehiculoRepository vehiculoRepository;
	private VehiculoServiceImpl vehiculoService;


	@Before
	public void setUp() {
		vehiculoRepository = Mockito.mock(VehiculoRepository.class);
		vehiculoService = new VehiculoServiceImpl(vehiculoRepository);
	}

	@Test
	public void agregarVehiculoExitoTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		VehiculoServiceImpl vehiculoServiceSpy = Mockito.spy(vehiculoService);
		Mockito.doReturn(null).when(vehiculoServiceSpy).buscarVehiculoPorPlaca(vehiculo.getPlaca());
		Mockito.doNothing().when(vehiculoServiceSpy).validarCupoParqueadero(vehiculo);
		Mockito.doNothing().when(vehiculoServiceSpy).verificarPlacaPermitida(vehiculo.getPlaca());
		verify(vehiculoRepository, never()).save((any(VehiculoEntity.class)));
		// act
		vehiculoServiceSpy.agregarVehiculoParqueadero(vehiculo);
		// assert
		Assert.assertNull(vehiculoServiceSpy.buscarVehiculoPorPlaca(vehiculo.getPlaca()));
	}

	@Test
	public void agregarMotoSinCupoTest() {
		// arrange
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PTT26H").build();
		List<Vehiculo> listaVehiculos = datosAgregarMotoSinCupoTest();
		VehiculoServiceImpl vehiculoServiceSpy = Mockito.spy(vehiculoService);
		Mockito.doReturn(listaVehiculos).when(vehiculoServiceSpy).obtenerVehiculos();
		try {
			// act
			vehiculoServiceSpy.agregarVehiculoParqueadero(vehiculoTest);
		} catch (VehiculoException e) {
			// assert
			Assert.assertEquals(VehiculoServiceImpl.SIN_CUPO, e.getMessage());
		}
	}


	@Test
	public void agregarCarroSinCupoTest() {
		// arrange
		List<Vehiculo> listaVehiculos = datosAgregarCarroSinCupoTest();
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("PII21k").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		VehiculoServiceImpl vehiculoServiceSpy = Mockito.spy(vehiculoService);
		Mockito.doReturn(listaVehiculos).when(vehiculoServiceSpy).obtenerVehiculos();
		try {
			// act
			vehiculoServiceSpy.agregarVehiculoParqueadero(vehiculoTest);
		} catch (VehiculoException e) {
			// assert
			Assert.assertEquals(VehiculoServiceImpl.SIN_CUPO, e.getMessage());
		}
	}

	@Test
	public void verificarPlacaNoPermitidaTest() throws ParseException {
		// arrange
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateInString = "11/01/2019";
		Date fecha = formatter.parse(dateInString);
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("AII21k").conFechaIngreso(fecha).build();
		VehiculoServiceImpl vehiculoServiceSpy = Mockito.spy(vehiculoService);
		Mockito.doNothing().when(vehiculoServiceSpy).validarCupoParqueadero(vehiculoTest);
		try {
			// act
			vehiculoServiceSpy.agregarVehiculoParqueadero(vehiculoTest);
		} catch (VehiculoException e) {
			// assert
			Assert.assertEquals(VehiculoServiceImpl.VEHICULO_NO_AUTORIZADO, e.getMessage());
		}
	}

	@Test
	public void actualizarVehiculoTest() throws ParseException {
		// arrange
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateInString = "11/01/2019";
		Date fecha = formatter.parse(dateInString);
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("ZII21k").conFechaIngreso(fecha)
				.conEstaParqueado(Boolean.FALSE).build();
		VehiculoServiceImpl vehiculoServiceSpy = Mockito.spy(vehiculoService);
		Mockito.doReturn(vehiculoTest).when(vehiculoServiceSpy).buscarVehiculoPorPlaca(vehiculoTest.getPlaca());
		Mockito.doNothing().when(vehiculoServiceSpy).validarCupoParqueadero(vehiculoTest);
		Mockito.doNothing().when(vehiculoServiceSpy).verificarPlacaPermitida(vehiculoTest.getPlaca());
		verify(vehiculoRepository, never()).save((any(VehiculoEntity.class)));
		// act
		vehiculoServiceSpy.agregarVehiculoParqueadero(vehiculoTest);
	}


	@Test
	public void buscarVehiculoPorPlacaExitoTest() {
		// arrange
		Vehiculo vehiculoTest = new VehiculoTestDataBuilder().conPlaca("LTT26H").build();
		VehiculoEntity vehiculoEntity = VehiculoBuilder.convertirAEntity(vehiculoTest);
		when(vehiculoRepository.buscarVehiculoPorPlaca("LTT26H")).thenReturn(vehiculoEntity);
		// act
		Vehiculo vehiculoConsultado = vehiculoService.buscarVehiculoPorPlaca(vehiculoTest.getPlaca());
		// assert
		Assert.assertNotNull(vehiculoConsultado);
	}

	@Test
	public void buscarVehiculoPorPlacaNullTest() {
		// arrange
		when(vehiculoRepository.buscarVehiculoPorPlaca("LTT26H")).thenReturn(null);
		// act
		Vehiculo vehiculoConsultado = vehiculoService.buscarVehiculoPorPlaca("LZT50H");
		// assert
		Assert.assertNull(vehiculoConsultado);
	}

	@Test
	public void obtenerVehiculosTest() {
		// arrange
		List<VehiculoEntity> listaVehiculosEntity = datosObtenerVehiculosTest();
		when(vehiculoRepository.obtenerVehiculos()).thenReturn(listaVehiculosEntity);
		// act
		List<Vehiculo> listaVehiculos = vehiculoService.obtenerVehiculos();
		// assert
		Assert.assertNotNull(listaVehiculos);
		Assert.assertEquals(listaVehiculosEntity.size(), listaVehiculos.size());
	}


	private List<VehiculoEntity> datosObtenerVehiculosTest() {
		List<VehiculoEntity> listaVehiculosEntity = new ArrayList<>();
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		listaVehiculosEntity.add(VehiculoBuilder.convertirAEntity(vehiculo));
		Vehiculo vehiculo1 = new VehiculoTestDataBuilder().conPlaca("PTT29G").build();
		listaVehiculosEntity.add(VehiculoBuilder.convertirAEntity(vehiculo1));
		return listaVehiculosEntity;

	}

	private List<Vehiculo> datosAgregarMotoSinCupoTest() {
		List<Vehiculo> listaVehiculos = new ArrayList<>();
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		listaVehiculos.add(vehiculo);
		Vehiculo vehiculo1 = new VehiculoTestDataBuilder().conPlaca("PTT29G").build();
		listaVehiculos.add(vehiculo1);
		Vehiculo vehiculo2 = new VehiculoTestDataBuilder().conPlaca("PTT27G").build();
		listaVehiculos.add(vehiculo2);
		Vehiculo vehiculo3 = new VehiculoTestDataBuilder().conPlaca("PTT29W").build();
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

	private List<Vehiculo> datosAgregarCarroSinCupoTest() {
		List<Vehiculo> listaVehiculos = new ArrayList<>();
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		listaVehiculos.add(vehiculo);
		Vehiculo vehiculo1 = new VehiculoTestDataBuilder().conPlaca("PTT29G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo1);
		Vehiculo vehiculo2 = new VehiculoTestDataBuilder().conPlaca("PTT27G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo2);
		Vehiculo vehiculo3 = new VehiculoTestDataBuilder().conPlaca("PTT45Z").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo3);
		Vehiculo vehiculo4 = new VehiculoTestDataBuilder().conPlaca("YTT46G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo4);
		Vehiculo vehiculo5 = new VehiculoTestDataBuilder().conPlaca("GTT47G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo5);
		Vehiculo vehiculo6 = new VehiculoTestDataBuilder().conPlaca("EBT48G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo6);
		Vehiculo vehiculo7 = new VehiculoTestDataBuilder().conPlaca("PTT49Y").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo7);
		Vehiculo vehiculo8 = new VehiculoTestDataBuilder().conPlaca("PTT50G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo8);
		Vehiculo vehiculo9 = new VehiculoTestDataBuilder().conPlaca("PTT51G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo9);
		Vehiculo vehiculo10 = new VehiculoTestDataBuilder().conPlaca("PTB52G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo10);
		Vehiculo vehiculo11 = new VehiculoTestDataBuilder().conPlaca("PCC53G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo11);
		Vehiculo vehiculo12 = new VehiculoTestDataBuilder().conPlaca("ZXT54G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo12);
		Vehiculo vehiculo13 = new VehiculoTestDataBuilder().conPlaca("MTT55G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo13);
		Vehiculo vehiculo14 = new VehiculoTestDataBuilder().conPlaca("GTT56G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo14);
		Vehiculo vehiculo15 = new VehiculoTestDataBuilder().conPlaca("EWT57G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo15);
		Vehiculo vehiculo16 = new VehiculoTestDataBuilder().conPlaca("PTT58Y").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo16);
		Vehiculo vehiculo17 = new VehiculoTestDataBuilder().conPlaca("QQQ59G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo17);
		Vehiculo vehiculo18 = new VehiculoTestDataBuilder().conPlaca("PPP60G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo18);
		Vehiculo vehiculo19 = new VehiculoTestDataBuilder().conPlaca("PII61G").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo19);
		Vehiculo vehiculo20 = new VehiculoTestDataBuilder().conPlaca("PII89X").conTipo(TipoVehiculoEnum.CARRO.name())
				.build();
		listaVehiculos.add(vehiculo20);
		return listaVehiculos;
	}

}
