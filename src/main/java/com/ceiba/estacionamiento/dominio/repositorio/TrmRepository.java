package com.ceiba.estacionamiento.dominio.repositorio;

import java.rmi.RemoteException;

import com.ceiba.estacinamiento.dominio.Trm;
/**
 * interface que expone el servicio para consultar el trm
 * @author jhon.bedoya
 *
 */
public interface TrmRepository {

	/**
	 * Metodo encargado de retornar el TRM actual desde el webservice de la
	 * superintendencia financiera de colombia
	 * 
	 * @return Trm, objeto con la informacion obtenida del webservice
	 */
	Trm obtenerTrm() throws RemoteException;

}
