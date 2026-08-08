package com.tienda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "tbCategorias")
public class Categoria {
	
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	@Id
	private int idCategoria; 
	
	@NotBlank(message = "Descripcion de la categoria es obligatorio")
	private String descripcion; 
	
	//@NotBlank(message = "Seleccione el estado de la categoria")
	private int estCategoria; 
	
	/*
	 * idCategoria INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(50),
    estCategoria INT DEFAULT 1
	 * */
}
