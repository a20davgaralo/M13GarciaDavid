package com.dga.springboot.m13garciadavid;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Gestiona diferents configuracions relatives al projecte
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Gestiona la vista que es mostra si entrem a una pàgina on no tenim permisos. Canvia la vista "Forbidden" per una personalitzada
     * En aquest cas, pasem al mètode addViewController la vista personalitzada error_403
     * @param registry
     */
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/error_403").setViewName("error_403");
    }
}
