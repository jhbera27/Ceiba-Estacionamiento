package com.ceiba.estacionamiento.persistencia.service;

import java.rmi.RemoteException;

import com.ceiba.estacinamiento.dominio.Trm;

public interface TrmService {
	
	Trm obtenerTrm() throws RemoteException;

}
