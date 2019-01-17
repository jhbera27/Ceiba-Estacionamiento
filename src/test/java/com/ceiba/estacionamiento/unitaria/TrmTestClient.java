package com.ceiba.estacionamiento.unitaria;

import static org.mockito.Mockito.when;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.ceiba.estacinamiento.dominio.Trm;
import com.ceiba.estacionamiento.persistencia.repositorio.TrmPersistenciaRepository;
import com.ceiba.estacionamiento.trm.TCRMServicesInterfaceProxy;
import com.ceiba.estacionamiento.trm.TcrmResponse;

/**
 * Clase para probar el servicio web de la superintendencia
 * 
 * @author jhon.bedoya
 *
 */
public class TrmTestClient {
	/**
	 * instancia de la clase para consumir el servico web de la superintendencia
	 */
	@Mock
	TCRMServicesInterfaceProxy servicesInterfaceProxy;
	/**
	 * instancia del repositorio para realizar la prueba unitaria
	 */
	@InjectMocks
	private TrmPersistenciaRepository trmPersistenciaRepository;

	/**
	 * test encargado de verificar que se consulte exitosamente del servicio web de
	 * la superintendencia
	 * 
	 * @throws RemoteException
	 */
	public void conexionWebServiceTest() throws RemoteException {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		TcrmResponse tcrmResponse = new TcrmResponse();
		when(servicesInterfaceProxy.queryTCRM(calendar)).thenReturn(tcrmResponse);
		Trm trm = trmPersistenciaRepository.obtenerTrm();
		Assert.assertNotNull(trm);
	}

}
