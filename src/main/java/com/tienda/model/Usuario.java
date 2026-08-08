package com.tienda.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name="tbUsuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codUsua; 
	
	@NotBlank(message = "El nombre es obligatorio")
	private String nomUsua; 
	
	@NotBlank(message = "El apellido es obligatorio")
	private String apeUsua; 
	
	@NotBlank(message = "El dni es obligatorio")
	@Size(min = 8,max = 8, message = "El dni tiene que tener 8 digitos exactos")
	@Pattern(regexp = "\\d{8}", message = "El dni debe contenedor números")
	private String dni;
	
	@NotBlank(message = "El número telefonico es obligatorio")
	@Size(min = 9, max = 9, message = "El telefono debe empezar con 9")
	private String telefono; 
	
	@NotBlank(message = "El correo es obligatorio")
	@Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.(com|pe|net|org)$", message = "Ingrese un correo válido" )
	private String email; 
	
	//@NotBlank(message = "La contraseña es obligatoria")
	//@Size(min = 6, message = "La contraseña debe tener minimo 6 caracteres")
	private String password; 
	
    private java.time.LocalDate fnaUsua;
    
	//@NotBlank(message = "Selleccione un tipo")
	private int idTipo ; 
	private int estUsua; 
	
	
	@ManyToOne
	@JoinColumn(name="idTipo",insertable = false,updatable = false)
	private Tipo objTipo; 
	
	
	
}
