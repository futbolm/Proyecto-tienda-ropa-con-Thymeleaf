package com.tienda.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tbCabBoleta")
public class CabBoleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numBol;

    private LocalDate fecha;
    private double total;
    private int codUsua;

    @ManyToOne
    @JoinColumn(name = "codUsua", insertable = false, updatable = false)
    private Usuario objUsuario;
    
    @OneToMany(mappedBy = "objBoleta")
    private List<DetBoleta> detalle; // ✅ AHORA SÍ EXISTE
}