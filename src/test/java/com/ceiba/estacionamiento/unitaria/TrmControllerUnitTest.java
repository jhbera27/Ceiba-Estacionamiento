package com.ceiba.estacionamiento.unitaria;

import java.rmi.RemoteException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.ceiba.estacinamiento.dominio.Trm;
import com.ceiba.estacionamiento.controller.TrmController;
import com.ceiba.estacionamiento.persistencia.service.TrmService;
import com.ceiba.estacionamiento.testdatabuilder.TrmTestDataBuilder;

public class TrmControllerUnitTest {
	
	private TrmService trmService;
	private TrmController trmController;
	
	@Before
	public void setUp() {
		trmService = Mockito.mock(TrmService.class);
		trmController = new TrmController(trmService);
	}
	
	@Test
	public void obtenerTrmTest() throws RemoteException {
		Trm trm = new TrmTestDataBuilder().build();
		Mockito.when(trmService.obtenerTrm()).thenReturn(trm);
		Trm trmConsultado  = trmController.obtenerTrm();
		Assert.assertEquals(trmConsultado, trm);
	}
}
