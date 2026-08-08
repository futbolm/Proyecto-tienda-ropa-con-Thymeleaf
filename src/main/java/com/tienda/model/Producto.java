package com.tienda.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;



import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Data
@Table(name = "tbProductos")
public class Producto {
	
	
	@Id
	@NotBlank(message = "El codigo de producto es obligatorio")
	private String idProd; 
	@NotBlank(message = "La descipcion del producto es obligatorio")
	private String desProd; 
	@PositiveOrZero(message = "El stock no puede ser negativo")
	private int stkProd; 
	@Positive(message = "El precio debe ser mayor a cero")
	private Double preProd; 
	
	private LocalDate fechaRegistro;
	
	@Min(value = 1, message = "Seleccione una categoría")
	private int idCategoria; 
	
	private int idProveedor; 
	
	@NotBlank(message = "La talla es obligatoria")
	private String talla; 
	
	
	@NotBlank(message = "El color es obligatorio")
	private String color; 
	

	private String imagen; 
	
	private int estProd; 
	
	/*private LocalDateTime fechaCreacion;*/

    private LocalDateTime fechaActualizacion;
    
    
    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDate.now();
       /* this.fechaCreacion = LocalDateTime.now();*/
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    @ManyToOne
    @JoinColumn(name = "idCategoria",insertable = false,updatable = false)
    private Categoria objCategoria; 
    
    @ManyToOne
    @JoinColumn(name = "idProveedor",insertable = false,updatable = false)
    private Proveedor objProveedor; 
	
	/* @Id
    @NotBlank(message = "El código es obligatorio")
    private String id_prod;

    @NotBlank(message = "La descripción es obligatoria")
    private String des_prod;

    @PositiveOrZero(message = "El stock no puede ser negativo")
    private int stk_prod;

    @Positive(message = "El precio debe ser mayor a 0")
    private double pre_prod;

    @Min(value = 1, message = "Seleccione una categoría")
    private int idcategoria;

    private int est_prod;
    private String imagen;

    @Min(value = 1, message = "Seleccione un proveedor")
    private int idproveedor;

    @ManyToOne
    @JoinColumn(name = "idcategoria", insertable = false, updatable = false)
    private Categoria objCategoria;

    @ManyToOne
    @JoinColumn(name = "idproveedor", insertable = false, updatable = false)
    private Proveedor objProveedor;
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * */
    
    
    
    
	/*
	 * CREATE TABLE tbProductos (
    idProd CHAR(5) PRIMARY KEY,
    desProd VARCHAR(50),
    stkProd INT default 0,
    preProd DECIMAL(8,2),
    fechaRegistro DATE,
    
    idCategoria INT,
    idProveedor INT,
    
    talla VARCHAR(10),
    color VARCHAR(20),

    
    imagen VARCHAR(255) DEFAULT 'default.jpg',
    estProd INT DEFAULT 1,
    
    fechaCreacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fechaActualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (idCategoria) REFERENCES tbCategorias(idCategoria),
    FOREIGN KEY (idProveedor) REFERENCES tbProveedores(idProveedor)
);
	 * 
	 * */
}
