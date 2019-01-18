package com.ceiba.estacionamiento.persistencia.repositorio;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ceiba.estacinamiento.dominio.Trm;
import com.ceiba.estacionamiento.dominio.repositorio.TrmRepository;

import co.com.sc.nexura.superfinanciera.action.generic.services.trm.action.TCRMServicesInterfaceProxy;
import co.com.sc.nexura.superfinanciera.action.generic.services.trm.action.TcrmResponse;


/**
 * clase creada para obtener el trm de la superintendencia
 * 
 * @author jhon.bedoya
 *
 */
@Service
@Transactional
public class TrmPersistenciaRepository implements TrmRepository {
	/**
	 * Web Service end point
	 */
	private static final String WEB_SERVICE_URL = "https://www.superfinanciera.gov.co/SuperfinancieraWebServiceTRM/TCRMServicesWebService/TCRMServicesWebService?WSDL";

	/**
	 * método encargado de consumir el servio web por medio de un wsdl para
	 * consultar el trm de la superintendencia
	 * 
	 * @throws RemoteException, excepcion generada al intentar consultar el servicio
	 *                          remoto
	 */
	@Override
	public Trm obtenerTrm() throws RemoteException {
		TCRMServicesInterfaceProxy proxy = new TCRMServicesInterfaceProxy(WEB_SERVICE_URL);
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		TcrmResponse tcrmResponse = proxy.queryTCRM(calendar);
		return convertTcrmResponseToTrm(tcrmResponse);
	}

	/**
	 * método encargado de convertir la respuesta del servicio web en un obtejo trm
	 * de dominio
	 * 
	 * @param tcrmResponse, la respuesta del servidor
	 * @return Trm, la informacion consultada del servicio web
	 */
	private Trm convertTcrmResponseToTrm(TcrmResponse tcrmResponse) {
		Trm trm = new Trm();
		trm.setId(tcrmResponse.getId());
		trm.setFechaValidaDesde(tcrmResponse.getValidityFrom().getTime());
		trm.setFechaValidaHasta(tcrmResponse.getValidityTo().getTime());
		trm.setUnit(tcrmResponse.getUnit());
		trm.setValor(tcrmResponse.getValue());
		return trm;
	}

}
