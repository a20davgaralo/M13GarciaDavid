package com.dga.springboot.m13garciadavid.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Classe entity per gestionar la entitat i taula INFORME fent servir Hibernate
 */
@Entity
public class Informe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_informe;

    @NotEmpty
    private String numExp;

    private int numPag;

    private String nombreFichero;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    public Long getId_informe() {
        return id_informe;
    }

    public void setId_informe(Long id_informe) {
        this.id_informe = id_informe;
    }

    public String getNumExp() {
        return numExp;
    }

    public void setNumExp(String numExp) {
        this.numExp = numExp;
    }

    public int getNumPag() {
        return numPag;
    }

    public void setNumPag(int numPag) {
        this.numPag = numPag;
    }

    public String getNombreFichero() {
        return nombreFichero;
    }

    public void setNombreFichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
