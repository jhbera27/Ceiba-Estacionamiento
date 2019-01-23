package com.ceiba.estacionamiento.controller;

import java.rmi.RemoteException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ceiba.estacinamiento.dominio.Trm;
import com.ceiba.estacionamiento.persistencia.service.TrmService;

@RestController
@RequestMapping("/trm")
public class TrmController {
	
	@Autowired
	private TrmService trmService;
	
	public TrmController(TrmService trmService) {
		this.trmService = trmService;
	}
	
	@RequestMapping("/obtenerTrm")
	public Trm obtenerTrm() throws RemoteException {
		return trmService.obtenerTrm();
	}

}
