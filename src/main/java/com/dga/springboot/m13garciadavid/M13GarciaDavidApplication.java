package com.dga.springboot.m13garciadavid;

import com.dga.springboot.m13garciadavid.models.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Classe principal des d'on s'executa el programa
 */
@SpringBootApplication
public class M13GarciaDavidApplication implements CommandLineRunner {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    IUploadFileService uploadFileService;

    public static void main(String[] args) {
        SpringApplication.run(M13GarciaDavidApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //Mètode auxiliar que permet generar contrasenyes encriptades si volem afegir users directament a BBDD
       /* String password = "12345";

        for (int i = 0; i < 2; i++) {
            String bcryptPassword = passwordEncoder.encode(password);
            System.out.println(bcryptPassword);
        }*/

        //Aquests mètodes es poden fer servir per eliminar els arxius pujats cada vegada que iniciem sessió
        //uploadFileService.deleteAll();
        //uploadFileService.init();
    }
}
