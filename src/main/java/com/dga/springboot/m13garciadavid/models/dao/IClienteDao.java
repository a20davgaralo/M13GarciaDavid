package com.dga.springboot.m13garciadavid.models.dao;


import com.dga.springboot.m13garciadavid.models.entity.Cliente;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.PagingAndSortingRepository;

//Primer dato, nuestra clase entity y el tipo de dato de la llave primaria
public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

    //Parámetro ?1 es el primer parámetro del método. El LEFT es porque si no lo hacemos, al abrir un cliente que no tiene facturas, nos dice que el cliente no existe en la base de datos
    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.facturas f WHERE c.id = ?1")
    public Cliente fetchByIdWithFacturas(Long id);
}