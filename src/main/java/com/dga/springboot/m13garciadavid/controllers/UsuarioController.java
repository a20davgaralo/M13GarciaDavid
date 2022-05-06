package com.dga.springboot.m13garciadavid.controllers;

import com.dga.springboot.m13garciadavid.models.entity.Cliente;
import com.dga.springboot.m13garciadavid.models.entity.Usuario;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;
import java.util.Map;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Controlador per tota la part de l'alta d'usuaris a la BBDD
 */
@Controller
public class UsuarioController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private MessageSource messageSource;


    /**
     * Crear un usuari
     *
     * @param model
     * @return
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping("/usuario/alta")
    public String crearUsuario(Usuario usuario, Model model, BindingResult result, Locale locale) {

        model.addAttribute("titulo", messageSource.getMessage("texto.cliente.form.titulo.crear.usuario", null, locale));


        model.addAttribute("usuario", usuario);

        logger.info("Usuario guardado " + usuario.toString());

        /*Cliente cliente = new Cliente(); //Find one con el id usuario que pillo
        model.put("cliente", cliente);*/


        return "usuario/alta";
    }
}
