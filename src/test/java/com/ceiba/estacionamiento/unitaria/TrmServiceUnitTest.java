package com.ceiba.estacionamiento.unitaria;

import java.rmi.RemoteException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import com.ceiba.estacinamiento.dominio.Trm;
import com.ceiba.estacionamiento.persistencia.service.impl.TrmServiceImpl;

public class TrmServiceUnitTest {

	private TrmServiceImpl trmService;

	/**
	 * método encargado de inicializar el contexto de pertistencia para las pruebas
	 * unitarias
	 */
	@Before
	public void setUp() {
		trmService = new TrmServiceImpl();
	}

	@Test
	public void obtenerTrmTest() throws RemoteException {
		Trm trm = trmService.obtenerTrm();
		Assert.assertNotNull(trm);
		Assert.assertNotNull(trm.getValor());
	}

}
