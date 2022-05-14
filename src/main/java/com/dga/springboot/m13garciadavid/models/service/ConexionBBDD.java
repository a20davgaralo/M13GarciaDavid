package com.dga.springboot.m13garciadavid.models.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Classe per gestionar una connexió amb una BBDD.
 */
public class ConexionBBDD {

    //CONSTANTS PER DEFINIR LA CONNEXIÓ A LA BBDD
    private static final String URL = "jdbc:mysql://labs.inspedralbes.cat/a20davgaralo_projecteM13?useSSL=false";

    private static final String USUARI = "a20davgaralo_us";

    private static final String PASSWORD = "GarDavM06";

    /**
     * Mètode que crea una connexió amb la BBDD
     * @return
     */
    public static Connection obreConnexioBBDD() {
        Connection connexio = null;
        String url = URL;
        String usuari = USUARI;
        String password = PASSWORD;

        try {
            connexio = DriverManager.getConnection(url, usuari, password);
            System.out.println("Connexió realitzada fent servir" + " DriverManager");
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
        return connexio;
    }

    /** Mètode que tanca una connexió amb la BBDD
     */
    public static void tancaConnexioBBDD(Connection connexio) {
        try {
            connexio.close();
            //System.out.println("Connexió tancada correctament");
        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

}
