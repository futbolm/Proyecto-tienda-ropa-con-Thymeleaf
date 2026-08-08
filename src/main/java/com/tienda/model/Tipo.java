package com.tienda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "tbTipos")
public class Tipo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int idTipo; 
	
	private String descripcion; 
	
	/*
	 * CREATE TABLE tbTipos (
    idTipo INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(20)
);
	 * */
}
