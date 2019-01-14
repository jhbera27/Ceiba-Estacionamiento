package com.ceiba.estacionamiento.sistema;

import javax.persistence.EntityManager;

import com.ceiba.estacionamiento.dominio.repositorio.SalidaParqueaderoRepository;
import com.ceiba.estacionamiento.dominio.repositorio.VehiculoRepository;
import com.ceiba.estacionamiento.persistencia.conexion.ConexionJPA;
import com.ceiba.estacionamiento.persistencia.repositorio.SalidaParqueaderoPersistenciaRepository;
import com.ceiba.estacionamiento.persistencia.repositorio.VehiculoPersistenciaRepository;

/**
 * clase que permite realizar la inizializacion del sistema de persisitencia
 * para las pruebas unitarias
 * 
 * @author jhon.bedoya
 *
 */
public class SistemaDePersistencia {

	private EntityManager entityManager;

	/**
	 * constructor de la clase SistemaDePersistencia
	 */
	public SistemaDePersistencia() {
		this.entityManager = new ConexionJPA().createEntityManager();
	}

	/**
	 * método encargado de unicializar el VehiculoPersistenciaRepository con la
	 * conexion JPA
	 * 
	 * @return VehiculoRepository, el repository para permitir el acceso a los
	 *         métodos a probar
	 */
	public VehiculoRepository obtenerVehiculoRepository() {
		return new VehiculoPersistenciaRepository(entityManager);
	}

	/**
	 * método encargado de unicializar el SalidaParqueaderoPersistenciaRepository
	 * con la conexion JPA
	 * 
	 * @return SalidaParqueaderoRepository, el repository para permitir el acceso a
	 *         los métodos a probar
	 */
	public SalidaParqueaderoRepository obtenerSalidaParqueaderoRepository() {
		return new SalidaParqueaderoPersistenciaRepository(entityManager);
	}

	/**
	 * metodo encargado de inicializar el contexto de persistencia
	 */
	public void iniciar() {
		entityManager.getTransaction().begin();
	}

	/**
	 * metodo encargado de finalizar el contexto de persistencia
	 */
	public void terminar() {
		entityManager.getTransaction().commit();
	}
}
