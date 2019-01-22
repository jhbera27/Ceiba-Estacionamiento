package com.ceiba.estacionamiento.persistencia.repositorio;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ceiba.estacionamiento.persistencia.entity.SalidaParqueaderoEntity;

public interface SalidaParqueaderoRepository  extends JpaRepository<SalidaParqueaderoEntity, Long> {
	
	@Query("SELECT s FROM SalidaParqueaderoEntity s  ORDER BY s.fechaSalida")
	List<SalidaParqueaderoEntity> obtenerSalidaVehiculosParqueadero();

}
