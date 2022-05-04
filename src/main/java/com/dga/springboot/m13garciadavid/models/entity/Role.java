package com.dga.springboot.m13garciadavid.models.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Classe entity per gestionar la entitat i taula authoritie fent servir Hibernate
 */
@Entity
@Table(name = "authoritie", uniqueConstraints= {@UniqueConstraint(columnNames= {"user_id", "authority"})})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
