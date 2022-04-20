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
        //Generamos las contraseñas encriptadas

        String password = "12345";

        for (int i = 0; i < 2; i++) {
            //Generamos dos contraseñas encriptadas desde 12345. Una para el usuario admin y otra para David
            String bcryptPassword = passwordEncoder.encode(password);
            System.out.println(bcryptPassword);
        }

        uploadFileService.deleteAll();
        uploadFileService.init();
    }
}
