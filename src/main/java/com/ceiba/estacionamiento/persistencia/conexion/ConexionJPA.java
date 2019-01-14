package com.ceiba.estacionamiento.persistencia.conexion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * clase encargada de la inicializion de la conexion JPA
 * 
 * @author jhon.bedoya
 *
 */
public class ConexionJPA {
	/**
	 * atributo que representa el nombre de la aplicacion
	 */
	private static final String PARQUEADERO = "parqueadero";
	/**
	 * instanciacion del EntityManagerFactory
	 */
	private static EntityManagerFactory entityManagerFactory;

	/**
	 * contructor de la clase, donde se inicializa el entityManagerFactory
	 */
	public ConexionJPA() {
		entityManagerFactory = Persistence.createEntityManagerFactory(PARQUEADERO);
	}

	/**
	 * método encargado de la creacion del entityManager
	 * 
	 * @return EntityManager, la instancia del entity manager
	 */
	public EntityManager createEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
}
