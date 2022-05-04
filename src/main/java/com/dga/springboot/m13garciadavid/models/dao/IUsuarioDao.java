package com.dga.springboot.m13garciadavid.models.dao;

import com.dga.springboot.m13garciadavid.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

    public Usuario findByUsername(String username);

    //public Long getIdClientUser(String username);
}
