package com.dga.springboot.m13garciadavid.controllers;

import com.dga.springboot.m13garciadavid.models.dao.IClienteDao;
import com.dga.springboot.m13garciadavid.models.entity.Cliente;
import com.dga.springboot.m13garciadavid.models.entity.Usuario;
import com.dga.springboot.m13garciadavid.models.service.IClienteService;
import com.dga.springboot.m13garciadavid.models.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

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

    @Autowired
    private IClienteService clienteService;


    /**
     * Crear un usuari
     * @param model
     * @param locale
     * @return
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/usuario/alta")
    public String crearUsuario(Map<String, Object> model, Locale locale) {
        Usuario usuario = new Usuario();
        model.put("usuario", usuario);
        model.put("titulo", messageSource.getMessage("texto.cliente.form.titulo.crear.usuario", null, locale));
        return "usuario/alta";
    }

    /**
     * Crear un usuari
     *
     * @param model
     * @return
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping("/usuario/alta")
    public String guardarUsuario(Usuario usuario, Model model,
                               Locale locale, RedirectAttributes flash) {

        model.addAttribute("titulo", messageSource.getMessage("texto.cliente.form.titulo.crear.usuario", null, locale));


        model.addAttribute("usuario", usuario);

        logger.info("Usuario guardado " + usuario.toString());

        Cliente cliente = null;
        try {
            cliente = clienteService.findOne((long) usuario.getId_cliente());

        } catch (Exception e) {
            logger.info("Cliente creado " + cliente.toString());
            flash.addFlashAttribute("error", messageSource.getMessage("texto.cliente.flash.db.error", null, locale));
        }

        try {
            UserService.insertaUsuario(usuario);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        /*if(cliente == null) {
            flash.addFlashAttribute("error", messageSource.getMessage("texto.cliente.flash.db.error", null, locale));
        }*/



        return "usuario/alta";
    }
}
