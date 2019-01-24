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
import com.ceiba.estacinamiento.dominio.SalidaParqueadero;
import com.ceiba.estacinamiento.dominio.Vehiculo;
import com.ceiba.estacionamiento.persistencia.builder.SalidaParqueaderoBuilder;
import com.ceiba.estacionamiento.persistencia.entity.SalidaParqueaderoEntity;
import com.ceiba.estacionamiento.persistencia.entity.VehiculoEntity;
import com.ceiba.estacionamiento.persistencia.repositorio.SalidaParqueaderoRepository;
import com.ceiba.estacionamiento.persistencia.repositorio.VehiculoRepository;
import com.ceiba.estacionamiento.persistencia.service.impl.SalidaParqueaderoServiceImpl;
import com.ceiba.estacionamiento.testdatabuilder.SalidaParqueaderoTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

/**
 * clase que permite realizar las pruebas unitarias para la clase
 * SalidaParqueaderoPersistenciaRepository
 * 
 * @author jhon.bedoya
 *
 */
public class SalidaParqueaderoServiceUnitTest {

	private SalidaParqueaderoServiceImpl salidaParqueaderoService;
	private SalidaParqueaderoRepository salidaParqueaderoRepository;
	private VehiculoRepository vehiculoRepository;


	@Before
	public void setUp() {
		salidaParqueaderoRepository = Mockito.mock(SalidaParqueaderoRepository.class);
		vehiculoRepository = Mockito.mock(VehiculoRepository.class);
		salidaParqueaderoService = new SalidaParqueaderoServiceImpl(salidaParqueaderoRepository, vehiculoRepository);
	}


	@Test
	public void agregarSalidaParqueaderoMotoExitoTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		SalidaParqueadero salidaParqueadero = new SalidaParqueaderoTestDataBuilder().conVehiculoSalida(vehiculo)
				.build();
		List<SalidaParqueaderoEntity> listaSalidaParqueaderoEntity = new ArrayList<>();
		listaSalidaParqueaderoEntity.add(SalidaParqueaderoBuilder.convertirAEntity(salidaParqueadero));
		SalidaParqueaderoServiceImpl salidaParqueaderoServiceSpy = Mockito.spy(salidaParqueaderoService);
		Mockito.doReturn(listaSalidaParqueaderoEntity).when(salidaParqueaderoServiceSpy)
				.obtenerSalidaVehiculosParqueadero();
		Mockito.doReturn(new BigDecimal(15000)).when(salidaParqueaderoServiceSpy).calcularPrecioAPagar(vehiculo,
				SalidaParqueaderoServiceImpl.VALOR_HORA_MOTO, SalidaParqueaderoServiceImpl.VALOR_DIA_MOTO);
		verify(salidaParqueaderoRepository, never()).save((any(SalidaParqueaderoEntity.class)));
		verify(vehiculoRepository, never()).save((any(VehiculoEntity.class)));

		// act
		salidaParqueaderoServiceSpy.agregar(salidaParqueadero);
		// assert
		List<SalidaParqueadero> listaSalidaParqueadero = salidaParqueaderoServiceSpy
				.obtenerSalidaVehiculosParqueadero();
		Assert.assertNotNull(listaSalidaParqueadero);
		Assert.assertEquals(listaSalidaParqueaderoEntity.size(), listaSalidaParqueadero.size());
	}


	@Test
	public void agregarSalidaParqueaderoCarroExitoTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conTipo(TipoVehiculoEnum.CARRO.name()).build();
		SalidaParqueadero salidaParqueadero = new SalidaParqueaderoTestDataBuilder().conVehiculoSalida(vehiculo)
				.build();
		List<SalidaParqueaderoEntity> listaSalidaParqueaderoEntity = new ArrayList<>();
		listaSalidaParqueaderoEntity.add(SalidaParqueaderoBuilder.convertirAEntity(salidaParqueadero));
		SalidaParqueaderoServiceImpl salidaParqueaderoServiceSpy = Mockito.spy(salidaParqueaderoService);
		Mockito.doReturn(listaSalidaParqueaderoEntity).when(salidaParqueaderoServiceSpy)
				.obtenerSalidaVehiculosParqueadero();
		Mockito.doReturn(new BigDecimal(15000)).when(salidaParqueaderoServiceSpy).calcularPrecioAPagar(vehiculo,
				SalidaParqueaderoServiceImpl.VALOR_HORA_CARRO, SalidaParqueaderoServiceImpl.VALOR_DIA_CARRO);
		verify(salidaParqueaderoRepository, never()).save((any(SalidaParqueaderoEntity.class)));
		verify(vehiculoRepository, never()).save((any(VehiculoEntity.class)));

		// act
		salidaParqueaderoServiceSpy.agregar(salidaParqueadero);
		// assert
		List<SalidaParqueadero> listaSalidaParqueadero = salidaParqueaderoServiceSpy
				.obtenerSalidaVehiculosParqueadero();
		Assert.assertNotNull(listaSalidaParqueadero);
		Assert.assertEquals(listaSalidaParqueaderoEntity.size(), listaSalidaParqueadero.size());

	}


	@Test
	public void obtenerSalidaVehiculosParqueaderoTest() throws ParseException {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conTipo(TipoVehiculoEnum.CARRO.name()).build();
		SalidaParqueadero salidaParqueadero = new SalidaParqueaderoTestDataBuilder().conVehiculoSalida(vehiculo)
				.build();
		List<SalidaParqueaderoEntity> listaSalidaParqueaderoEntity = new ArrayList<>();
		listaSalidaParqueaderoEntity.add(SalidaParqueaderoBuilder.convertirAEntity(salidaParqueadero));
		when(salidaParqueaderoRepository.obtenerSalidaVehiculosParqueadero()).thenReturn(listaSalidaParqueaderoEntity);
		// act
		List<SalidaParqueadero> listaSalidaParqueadero = salidaParqueaderoService.obtenerSalidaVehiculosParqueadero();
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

		SalidaParqueaderoServiceImpl salidaParqueaderoServiceSpy = Mockito.spy(salidaParqueaderoService);
		Mockito.doReturn(4L).when(salidaParqueaderoServiceSpy).diferenciaHoras(fechaC.getTime());
		Mockito.doReturn(new BigDecimal(2000)).when(salidaParqueaderoServiceSpy).calcularPrecioPorHoras(4L,
				SalidaParqueaderoServiceImpl.VALOR_HORA_MOTO);
		// act
		BigDecimal precioAPagado = salidaParqueaderoServiceSpy.calcularPrecioAPagar(vehiculo,
				SalidaParqueaderoServiceImpl.VALOR_HORA_MOTO, SalidaParqueaderoServiceImpl.VALOR_DIA_MOTO);
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
		SalidaParqueaderoServiceImpl salidaParqueaderoServiceSpy = Mockito.spy(salidaParqueaderoService);
		Mockito.doReturn(10L).when(salidaParqueaderoServiceSpy).diferenciaHoras(fechaC.getTime());
		// act
		BigDecimal precioAPagado = salidaParqueaderoServiceSpy.calcularPrecioAPagar(vehiculo,
				SalidaParqueaderoServiceImpl.VALOR_HORA_CARRO, SalidaParqueaderoServiceImpl.VALOR_DIA_CARRO);
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
		SalidaParqueaderoServiceImpl salidaParqueaderoServiceSpy = Mockito.spy(salidaParqueaderoService);
		Mockito.doReturn(26L).when(salidaParqueaderoServiceSpy).diferenciaHoras(fechaC.getTime());
		// act
		BigDecimal precioAPagado = salidaParqueaderoServiceSpy.calcularPrecioAPagar(vehiculo,
				SalidaParqueaderoServiceImpl.VALOR_HORA_CARRO, SalidaParqueaderoServiceImpl.VALOR_DIA_CARRO);
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
		SalidaParqueaderoServiceImpl salidaParqueaderoServiceSpy = Mockito.spy(salidaParqueaderoService);
		Mockito.doReturn(35L).when(salidaParqueaderoServiceSpy).diferenciaHoras(fechaC.getTime());
		// act
		BigDecimal precioAPagado = salidaParqueaderoServiceSpy.calcularPrecioAPagar(vehiculo,
				SalidaParqueaderoServiceImpl.VALOR_HORA_CARRO, SalidaParqueaderoServiceImpl.VALOR_DIA_CARRO);
		// assert
		Assert.assertNotNull(precioAPagado);
		Assert.assertEquals(new BigDecimal(18000), precioAPagado);
	}

	@Test
	public void calcularPrecioPorHorasTest() {
		BigDecimal precioPorHoras = salidaParqueaderoService.calcularPrecioPorHoras(4l,
				SalidaParqueaderoServiceImpl.VALOR_HORA_CARRO);
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
