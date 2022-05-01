package com.dga.springboot.m13garciadavid.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Locale;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Controlador per tota la part de la gestio del login
 */

@Controller
public class LoginController {

    //Necesitem aquesta injecció per gestionar els missatges i el multiidioma
    @Autowired
    private MessageSource messageSource;

    /**
     * Gestió de la vista login. Comprova si l'usuari ja ha iniciat sessió o si hi ha un error a l'introduir les dades
     * També si la sessió s'ha tancat amb èxit
     * @param error
     * @param logout
     * @param model
     * @param principal
     * @param flash
     * @return
     */
    @GetMapping("/login")
    public String login(@RequestParam(value="error", required = false) String error,
                        @RequestParam(value="logout", required = false) String logout,
                        Model model, Principal principal, RedirectAttributes flash,
                        Locale locale) {

        if (principal != null) {
            flash.addFlashAttribute("info", messageSource.getMessage("texto.login.already", null, locale));
            return "redirect:/";
        }

        if(error != null) {
            model.addAttribute("error", messageSource.getMessage("texto.login.error", null, locale));
        }

        if(logout !=null) {
            model.addAttribute("success", messageSource.getMessage("texto.login.logout", null, locale));
            return "redirect:/";
        }

        return "login";
    }
}