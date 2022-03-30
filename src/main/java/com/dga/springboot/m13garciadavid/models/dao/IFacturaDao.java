package com.dga.springboot.m13garciadavid.models.dao;


import com.dga.springboot.m13garciadavid.models.entity.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IFacturaDao extends CrudRepository<Factura, Long> {

    //Método para realizar JOIN en la consulta de la factura y sacarla en la vista ver
    @Query("SELECT f FROM Factura f JOIN FETCH f.cliente c JOIN FETCH f.items i JOIN FETCH i.producto " +
            "WHERE f.id = ?1")
    public Factura fetchByIdWithClienteWithItemFacturaWithProdcuto(Long id);
}
