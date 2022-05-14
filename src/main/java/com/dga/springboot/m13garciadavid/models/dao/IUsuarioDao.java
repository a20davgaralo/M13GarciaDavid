package com.dga.springboot.m13garciadavid.models.dao;

import com.dga.springboot.m13garciadavid.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Interfície de tipus Dao (Data Acces Object) que gestiona certs aspectes de la classe Entity Usuario.
 */
public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

    public Usuario findByUsername(String username);

}
