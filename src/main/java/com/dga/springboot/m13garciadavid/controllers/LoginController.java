package com.dga.springboot.m13garciadavid.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value="error", required = false) String error,
                        @RequestParam(value="logout", required = false) String logout,
                        Model model, Principal principal, RedirectAttributes flash) {

        //Si ya ha iniciado sesión usamos el prinicipal si es null
        if (principal != null) {
            flash.addFlashAttribute("info", "Ya habías iniciado sesión");
            return "redirect:/";
        }

        if(error != null) {
            model.addAttribute("error", "El nombre de usuario o la contraseña introduciodos son incorrectos. Por favor, vuelva a intentarlo");
        }

        if(logout !=null) {
            model.addAttribute("success", "Sesión cerrada con éxito");
        }
        return "login";
    }
}