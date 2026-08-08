package com.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.model.DetBoleta;
import com.tienda.model.DetBoletaId;



@Repository
public interface IDetBoletaRepository extends JpaRepository<DetBoleta,DetBoletaId>{

}
