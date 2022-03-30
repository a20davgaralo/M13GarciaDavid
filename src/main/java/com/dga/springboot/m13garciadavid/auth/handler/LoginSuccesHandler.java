package com.dga.springboot.m13garciadavid.auth.handler;



import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Gestiona quan un usuari es logeja correctament
 */

@Component //S'anota amb component per fer un Bean de Spring i que es pugui injectar a la classe SpringSecurityConfig
public class LoginSuccesHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        //Obtenir missatges Flash. Es fa així perquè no es pot injectar el RedirectAttribute als paràmetres com als controladors
        SessionFlashMapManager flashMapManager = new SessionFlashMapManager();

        FlashMap flashMap = new FlashMap();

        //L'objecte authentication que ve als paràmetres ens permet reconèixer el nom de l'usuari
        flashMap.put("success", "Hola " + authentication.getName()+ ", has iniciado sesión con éxito!");

        flashMapManager.saveOutputFlashMap(flashMap, request, response);

        //Validem si s'ha autenticat i llavors guardem l'esdeveniment d'autenticació al log
        if(authentication != null) {
            logger.info("El usuario '"+authentication.getName()+"' ha iniciado sesión con éxito");
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
