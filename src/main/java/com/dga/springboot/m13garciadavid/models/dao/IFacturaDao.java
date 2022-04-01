package com.dga.springboot.m13garciadavid.models.dao;


import com.dga.springboot.m13garciadavid.models.entity.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Interfície de tipus Dao (Data Acces Object) que gestiona certs aspectes de la classe Entity Factura.
 */

public interface IFacturaDao extends CrudRepository<Factura, Long> {

    /**
     * Mètode que implementa la query anotada amb @Query sobre la taula factura i
     * mostra els resultats a la vista 'ver'
     * @param id
     * @return
     */
    @Query("SELECT f FROM Factura f JOIN FETCH f.cliente c JOIN FETCH f.items i JOIN FETCH i.producto " +
            "WHERE f.id = ?1")
    public Factura fetchByIdWithClienteWithItemFacturaWithProdcuto(Long id);
}
