package com.ceiba.estacionamiento.unitaria;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.establecimiento.enums.TipoVehiculoEnum;
import com.ceiba.estacinamiento.dominio.SalidaVehiculo;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.persistencia.builder.SalidaVehiculoBuilder;
import com.ceiba.estacionamiento.persistencia.entity.SalidaVehiculoEntity;
import com.ceiba.estacionamiento.persistencia.entity.VehiculoEntity;
import com.ceiba.estacionamiento.persistencia.repositorio.SalidaVehiculoRepository;
import com.ceiba.estacionamiento.persistencia.repositorio.VehiculoRepository;
import com.ceiba.estacionamiento.persistencia.service.impl.SalidaVehiculoServiceImpl;
import com.ceiba.estacionamiento.testdatabuilder.SalidaVehiculoTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

public class SalidaVehiculoServiceUnitTest {

	private SalidaVehiculoServiceImpl salidaParqueaderoService;
	private SalidaVehiculoRepository salidaParqueaderoRepository;
	private VehiculoRepository vehiculoRepository;


	@Before
	public void setUp() {
		salidaParqueaderoRepository = Mockito.mock(SalidaVehiculoRepository.class);
		vehiculoRepository = Mockito.mock(VehiculoRepository.class);
		salidaParqueaderoService = new SalidaVehiculoServiceImpl(salidaParqueaderoRepository, vehiculoRepository);
	}


	@Test
	public void agregarSalidaVehiculoTipoMotoExitoTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		SalidaVehiculo salidaParqueadero = new SalidaVehiculoTestDataBuilder().conVehiculoSalida(vehiculo)
				.build();
		List<SalidaVehiculoEntity> listaSalidaParqueaderoEntity = new ArrayList<>();
		listaSalidaParqueaderoEntity.add(SalidaVehiculoBuilder.convertirAEntity(salidaParqueadero));
		SalidaVehiculoServiceImpl salidaParqueaderoServiceSpy = Mockito.spy(salidaParqueaderoService);
		Mockito.doReturn(listaSalidaParqueaderoEntity).when(salidaParqueaderoServiceSpy)
				.obtenerHistorialVehiculos();
		Mockito.doReturn(new BigDecimal(15000)).when(salidaParqueaderoServiceSpy).calcularPrecioAPagar(vehiculo,
				SalidaVehiculoServiceImpl.VALOR_HORA_MOTO, SalidaVehiculoServiceImpl.VALOR_DIA_MOTO);
		verify(salidaParqueaderoRepository, never()).save((any(SalidaVehiculoEntity.class)));
		verify(vehiculoRepository, never()).save((any(VehiculoEntity.class)));

		// act
		salidaParqueaderoServiceSpy.registrarSalidaVehiculo(salidaParqueadero);
		// assert
		List<SalidaVehiculo> listaSalidaParqueadero = salidaParqueaderoServiceSpy
				.obtenerHistorialVehiculos();
		Assert.assertNotNull(listaSalidaParqueadero);
		Assert.assertEquals(listaSalidaParqueaderoEntity.size(), listaSalidaParqueadero.size());
	}


	@Test
	public void agregarSalidaVehiculoTipoCarroExitoTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conTipo(TipoVehiculoEnum.CARRO.name()).build();
		SalidaVehiculo salidaParqueadero = new SalidaVehiculoTestDataBuilder().conVehiculoSalida(vehiculo)
				.build();
		List<SalidaVehiculoEntity> listaSalidaParqueaderoEntity = new ArrayList<>();
		listaSalidaParqueaderoEntity.add(SalidaVehiculoBuilder.convertirAEntity(salidaParqueadero));
		SalidaVehiculoServiceImpl salidaParqueaderoServiceSpy = Mockito.spy(salidaParqueaderoService);
		Mockito.doReturn(listaSalidaParqueaderoEntity).when(salidaParqueaderoServiceSpy)
				.obtenerHistorialVehiculos();
		Mockito.doReturn(new BigDecimal(15000)).when(salidaParqueaderoServiceSpy).calcularPrecioAPagar(vehiculo,
				SalidaVehiculoServiceImpl.VALOR_HORA_CARRO, SalidaVehiculoServiceImpl.VALOR_DIA_CARRO);
		verify(salidaParqueaderoRepository, never()).save((any(SalidaVehiculoEntity.class)));
		verify(vehiculoRepository, never()).save((any(VehiculoEntity.class)));

		// act
		salidaParqueaderoServiceSpy.registrarSalidaVehiculo(salidaParqueadero);
		// assert
		List<SalidaVehiculo> listaSalidaParqueadero = salidaParqueaderoServiceSpy
				.obtenerHistorialVehiculos();
		Assert.assertNotNull(listaSalidaParqueadero);
		Assert.assertEquals(listaSalidaParqueaderoEntity.size(), listaSalidaParqueadero.size());

	}


	@Test
	public void obtenerSalidaVehiculosParqueaderoTest() throws ParseException {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conTipo(TipoVehiculoEnum.CARRO.name()).build();
		SalidaVehiculo salidaParqueadero = new SalidaVehiculoTestDataBuilder().conVehiculoSalida(vehiculo)
				.build();
		List<SalidaVehiculoEntity> listaSalidaParqueaderoEntity = new ArrayList<>();
		listaSalidaParqueaderoEntity.add(SalidaVehiculoBuilder.convertirAEntity(salidaParqueadero));
		when(salidaParqueaderoRepository.obtenerSalidaVehiculosParqueadero()).thenReturn(listaSalidaParqueaderoEntity);
		// act
		List<SalidaVehiculo> listaSalidaParqueadero = salidaParqueaderoService.obtenerHistorialVehiculos();
		// assert
		Assert.assertNotNull(listaSalidaParqueadero);
		Assert.assertEquals(listaSalidaParqueaderoEntity.size(), listaSalidaParqueadero.size());
	}


	@Test
	public void calcularPrecioAPagarPorHoras() throws ParseException {
		// arrange
		Date fecha = new Date();
		Calendar fechaC = Calendar.getInstance();
		fechaC.setTime(fecha);
		fechaC.set(Calendar.HOUR, fechaC.get(Calendar.HOUR) - 3);
		fechaC.set(Calendar.MINUTE, fechaC.get(Calendar.MINUTE) - 20);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conFechaIngreso(fechaC.getTime()).build();

		SalidaVehiculoServiceImpl salidaParqueaderoServiceSpy = Mockito.spy(salidaParqueaderoService);
		Mockito.doReturn(4L).when(salidaParqueaderoServiceSpy).diferenciaHoras(fechaC.getTime());
		Mockito.doReturn(new BigDecimal(2000)).when(salidaParqueaderoServiceSpy).calcularPrecioPorHoras(4L,
				SalidaVehiculoServiceImpl.VALOR_HORA_MOTO);
		// act
		BigDecimal precioAPagado = salidaParqueaderoServiceSpy.calcularPrecioAPagar(vehiculo,
				SalidaVehiculoServiceImpl.VALOR_HORA_MOTO, SalidaVehiculoServiceImpl.VALOR_DIA_MOTO);
		// assert
		Assert.assertNotNull(precioAPagado);
		Assert.assertEquals(new BigDecimal(4000), precioAPagado);
	}


	@Test
	public void calcularPrecioAPagarPorDia() throws ParseException {
		// arrange
		Date fecha = new Date();
		Calendar fechaC = Calendar.getInstance();
		fechaC.setTime(fecha);
		fechaC.set(Calendar.HOUR, fechaC.get(Calendar.HOUR) - 10);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conFechaIngreso(fechaC.getTime())
				.conTipo(TipoVehiculoEnum.CARRO.name()).build();
		SalidaVehiculoServiceImpl salidaParqueaderoServiceSpy = Mockito.spy(salidaParqueaderoService);
		Mockito.doReturn(10L).when(salidaParqueaderoServiceSpy).diferenciaHoras(fechaC.getTime());
		// act
		BigDecimal precioAPagado = salidaParqueaderoServiceSpy.calcularPrecioAPagar(vehiculo,
				SalidaVehiculoServiceImpl.VALOR_HORA_CARRO, SalidaVehiculoServiceImpl.VALOR_DIA_CARRO);
		// assert
		Assert.assertNotNull(precioAPagado);
		Assert.assertEquals(new BigDecimal(8000), precioAPagado);
	}

	@Test
	public void calcularPrecioAPagarPorMasDeUnDia() throws ParseException {
		// arrange
		Date fecha = new Date();
		Calendar fechaC = Calendar.getInstance();
		fechaC.setTime(fecha);
		fechaC.set(Calendar.DATE, fechaC.get(Calendar.DATE) - 1);
		fechaC.set(Calendar.HOUR, fechaC.get(Calendar.HOUR) - 2);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conFechaIngreso(fechaC.getTime())
				.conTipo(TipoVehiculoEnum.CARRO.name()).build();
		SalidaVehiculoServiceImpl salidaParqueaderoServiceSpy = Mockito.spy(salidaParqueaderoService);
		Mockito.doReturn(26L).when(salidaParqueaderoServiceSpy).diferenciaHoras(fechaC.getTime());
		// act
		BigDecimal precioAPagado = salidaParqueaderoServiceSpy.calcularPrecioAPagar(vehiculo,
				SalidaVehiculoServiceImpl.VALOR_HORA_CARRO, SalidaVehiculoServiceImpl.VALOR_DIA_CARRO);
		// assert
		Assert.assertNotNull(precioAPagado);
		Assert.assertEquals(new BigDecimal(10000), precioAPagado);
	}

	@Test
	public void calcularPrecioAPagarPorMasDeUnDiaConHoras() throws ParseException {
		// arrange
		Date fecha = new Date();
		Calendar fechaC = Calendar.getInstance();
		fechaC.setTime(fecha);
		fechaC.set(Calendar.DATE, fechaC.get(Calendar.DATE) - 1);
		fechaC.set(Calendar.HOUR, fechaC.get(Calendar.HOUR) - 10);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conFechaIngreso(fechaC.getTime())
				.conTipo(TipoVehiculoEnum.CARRO.name()).build();
		SalidaVehiculoServiceImpl salidaParqueaderoServiceSpy = Mockito.spy(salidaParqueaderoService);
		Mockito.doReturn(35L).when(salidaParqueaderoServiceSpy).diferenciaHoras(fechaC.getTime());
		// act
		BigDecimal precioAPagado = salidaParqueaderoServiceSpy.calcularPrecioAPagar(vehiculo,
				SalidaVehiculoServiceImpl.VALOR_HORA_CARRO, SalidaVehiculoServiceImpl.VALOR_DIA_CARRO);
		// assert
		Assert.assertNotNull(precioAPagado);
		Assert.assertEquals(new BigDecimal(18000), precioAPagado);
	}

	@Test
	public void calcularPrecioPorHorasTest() {
		BigDecimal precioPorHoras = salidaParqueaderoService.calcularPrecioPorHoras(4l,
				SalidaVehiculoServiceImpl.VALOR_HORA_CARRO);
		// assert
		Assert.assertNotNull(precioPorHoras);
		Assert.assertEquals(new BigDecimal(4000), precioPorHoras);
	}

	@Test
	public void diferenciaHorasMenorAUnaHoraTest() {
		Date fecha = new Date();
		Calendar fechaC = Calendar.getInstance();
		fechaC.setTime(fecha);
		fechaC.set(Calendar.MINUTE, fechaC.get(Calendar.MINUTE) - 20);

		Long diferencia = salidaParqueaderoService.diferenciaHoras(fechaC.getTime());

		Assert.assertNotNull(diferencia);
		Assert.assertEquals(new Long(1), diferencia);
	}
	
	@Test
	public void diferenciaHorasMayorAUnaHoraTest() {
		Date fecha = new Date();
		Calendar fechaC = Calendar.getInstance();
		fechaC.setTime(fecha);
		fechaC.set(Calendar.HOUR, fechaC.get(Calendar.HOUR) - 10);

		Long diferencia = salidaParqueaderoService.diferenciaHoras(fechaC.getTime());

		Assert.assertNotNull(diferencia);
		Assert.assertEquals(new Long(11), diferencia);
	}

}
