package com.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tienda.model.CabBoleta;

@Repository
public interface ICabBoletaRepository extends JpaRepository<CabBoleta, Integer> {
	@Query("SELECT b FROM CabBoleta b WHERE b.codUsua = :codUsua")
	List<CabBoleta> findByCodUsua(@Param("codUsua") int codUsua);
}
