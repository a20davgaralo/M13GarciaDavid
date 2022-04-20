package com.dga.springboot.m13garciadavid.models.dao;


import com.dga.springboot.m13garciadavid.models.entity.Cliente;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Interfície de tipus Dao (Data Acces Object) que gestiona certs aspectes de la classe Entity Cliente.
 */

//Primera dada del PagingAndSortingRepository és la classe que gestiona i la segona el tipus de dada de la seva clau primària
public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

    /**
     * Mètode que realitza una cerca (fent servir la query anotada amb @Query)
     * a la taula client fent un join amb factura per mostrar les factures d'un client
     * Es fa un LEFT JOIN perquè si no es fa així, si un client no té factures no obtindríem cap resultat i no es
     * mostrarà al llistat.
     * @param id Està relacionat amb el ?1 de la query, el qual representa el primer paràmetre del mètode
     * @return
     */
    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.facturas f WHERE c.id = ?1")
    public Cliente fetchByIdWithFacturas(Long id);

    @Modifying
    @Query("UPDATE Cliente c SET c.informe = '' WHERE c.id =?1")
    public void setInformeVoid(Long id);
}