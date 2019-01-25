package com.ceiba.estacionamiento.persistencia.repositorio;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ceiba.estacionamiento.persistencia.entity.SalidaVehiculoEntity;

public interface SalidaVehiculoRepository  extends JpaRepository<SalidaVehiculoEntity, Long> {
	
	@Query("SELECT s FROM SalidaVehiculoEntity s  ORDER BY s.fechaSalida")
	List<SalidaVehiculoEntity> obtenerSalidaVehiculosParqueadero();

}
