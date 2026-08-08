package com.tienda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name ="tbProveedores")
public class Proveedor {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int idProveedor; 
	
	@NotBlank(message = "El nombre es obligatorio")
	private String nombre; 
	
	@NotBlank(message = "El telefono es obligario")
	@NotBlank(message = "El número telefonico es obligatorio")
	@Size(min = 9, max = 9, message = "El telefono debe empezar con 9")
	private String telefono; 
	
	@NotBlank(message = "El correo es obligatorio")
	@Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.(com|pe|net|org)$", message = "Ingrese un correo válido" )
	private String email; 
	
	private int estProveedor; 
/*  idProveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    telefono VARCHAR(15),
    email VARCHAR(50),
    estProveedor INT DEFAULT 1
 * 
 * 
 * */
}
