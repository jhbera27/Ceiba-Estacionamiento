package com.ceiba.estacionamiento.integracion;

import java.rmi.RemoteException;
import org.junit.Assert;
import com.ceiba.estacinamiento.dominio.Trm;
import com.ceiba.estacionamiento.persistencia.repositorio.TrmPersistenciaRepository;

/**
 * Clase para probar el servicio web de la superintendencia
 * 
 * @author jhon.bedoya
 *
 */
public class TrmTestClient {
	/**
	 * instancia del repositorio para la prueba de intengracion
	 */
	private TrmPersistenciaRepository trmPersistenciaRepository;

	/**
	 * test encargado de verificar que se consulte exitosamente del servicio web de
	 * la superintendencia
	 * 
	 * @throws RemoteException
	 */
	public void conexionWebServiceTest() throws RemoteException {
		Trm trm = trmPersistenciaRepository.obtenerTrm();
		Assert.assertNotNull(trm);
	}

}
