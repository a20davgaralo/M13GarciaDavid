package com.dga.springboot.m13garciadavid.models.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Classe entity per gestionar la entitat i taula FACTURA fent servir Hibernate
 */
@Entity
@Table(name="FACTURAS_ITEMS")
public class ItemFactura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double calcularImporte() {
        return cantidad.doubleValue() * producto.getPrecio();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }


    private static final long serialVersionUID = 4550358501443077906L;
}