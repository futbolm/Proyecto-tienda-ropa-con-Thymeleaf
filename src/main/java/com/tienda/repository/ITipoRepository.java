package com.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.model.Tipo;

@Repository
public interface ITipoRepository extends JpaRepository<Tipo, Integer>{

}
