package com.ceiba.estacionamiento.controller;

import java.rmi.RemoteException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceiba.estacinamiento.dominio.Trm;
import com.ceiba.estacionamiento.dominio.repositorio.TrmRepository;

@RestController
@RequestMapping("/trm")
public class TrmController {
	
	private TrmRepository trmRepository;
	
	public TrmController(TrmRepository trmRepository) {
		this.trmRepository = trmRepository;
	}
	
	@RequestMapping("/obtenerTrm")
	public Trm obtenerTrm() throws RemoteException {
		return trmRepository.obtenerTrm();
	}

}
