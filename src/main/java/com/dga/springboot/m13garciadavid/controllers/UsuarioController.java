package com.dga.springboot.m13garciadavid.controllers;

import com.dga.springboot.m13garciadavid.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;
import java.util.Map;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Controlador per tota la part de l'alta d'usuaris a la BBDD
 */
@Controller
public class UsuarioController {

    @Autowired
    private MessageSource messageSource;


    /**
     * Crear un usuari
     *
     * @param model
     * @return
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/usuario/alta")
    public String crear(Map<String, Object> model, Locale locale) {
        Cliente cliente = new Cliente();
        model.put("cliente", cliente);
        model.put("titulo", messageSource.getMessage("texto.cliente.form.titulo.crear.usuario", null, locale));
        return "usuario/alta";
    }
}
