package com.tienda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tbDetBoleta")
@IdClass(DetBoletaId.class)
public class DetBoleta {

    @Id 
    private int numBol;

    @Id
    private String idProd;

    private int cantidad;
    private double precio;      // antes preciovta

    @ManyToOne
    @JoinColumn(name = "numBol", insertable = false, updatable = false)
    private CabBoleta objBoleta;

    @ManyToOne
    @JoinColumn(name = "idProd", insertable = false, updatable = false)
    private Producto objProducto;
}