package com.ceiba.estacionamiento.persistencia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ceiba.estacionamiento.persistencia.entity.VehiculoEntity;

public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Long> {
	
	@Query("SELECT v FROM VehiculoEntity v WHERE v.estaParqueado = 1 ORDER BY v.placa")
    List<VehiculoEntity> obtenerVehiculos();
	
	@Query("SELECT v FROM VehiculoEntity v WHERE v.placa =:placa")
	VehiculoEntity buscarVehiculoPorPlaca(@Param("placa") String placa);

}
