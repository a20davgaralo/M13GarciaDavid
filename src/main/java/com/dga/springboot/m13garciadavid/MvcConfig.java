package com.dga.springboot.m13garciadavid;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

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

    /**
     * Mètode que gestiona la localització i l'idioma en que mostrar l'aplicació. Guarda el paràmetre a la sessió
     * @return l'objecte LocaleResolver amb la localització
     */
    @Bean
    public LocaleResolver localeResolver () {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("es", "ES"));
        return localeResolver;
    }

    /**
     * Interceptor per a canviar els texts de l'aplicació cada vegada que canviem d'idioma
     * @return l'objecte LocaleChangeInterceptor
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        return localeInterceptor;
    }

    /**
     * Implementació de l'interceptor
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
