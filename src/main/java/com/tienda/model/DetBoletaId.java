package com.tienda.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class DetBoletaId implements Serializable {
    private int numBol;      // antes num_bol
    private String idProd;   // antes id_prod
}