package com.dga.springboot.m13garciadavid.models.dao;

import com.dga.springboot.m13garciadavid.models.entity.Factura;
import com.dga.springboot.m13garciadavid.models.entity.Informe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Interfície de tipus Dao (Data Acces Object) que gestiona certs aspectes de la classe Entity Informe.
 */

public interface I_InformeDao extends CrudRepository<Informe, Long> {

    /**
     * Mètode que implementa la query anotada amb @Query sobre la taula factura i
     * mostra els resultats a la vista 'ver'
     * @param id
     * @return
     */
    @Query("SELECT i FROM Informe i JOIN FETCH i.cliente c WHERE i.id_informe = ?1")
    public Informe fetchByIdWithCliente(Long id);
}
