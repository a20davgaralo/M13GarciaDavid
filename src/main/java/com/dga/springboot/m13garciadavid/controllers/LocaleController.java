package com.dga.springboot.m13garciadavid.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Controlador per la gestió dels idiomes. Afegeix diferents paràmetres a la URL i no deixa que es perdi cap
 */

@Controller
public class LocaleController {

    /**
     * Mètode handler per gestionar la petició i redirigir a una ruta afegint paràmetres
     * @return un String amb la ruta per redirigir
     */
    @GetMapping("/locale")
    public String locale(HttpServletRequest request) {
        String ultimaURL = request.getHeader("referer");

        return "redirect:".concat(ultimaURL);
    }
}
