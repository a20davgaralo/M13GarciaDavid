package com.dga.springboot.m13garciadavid.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Classe entity per gestionar la entitat i taula PRODUCTO fent servir Hibernate
 */
@Entity
@Table(name = "PRODUCTO")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precio;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_At")
    private Date createAt;

    @PrePersist
    public void prePersist() {
        createAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }


    private static final long serialVersionUID = 4739662868926192771L;
}
