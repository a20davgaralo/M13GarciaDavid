package com.dga.springboot.m13garciadavid.models.service;

import com.dga.springboot.m13garciadavid.models.entity.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Author: David García Alonso
 *  Versió: 1.0
 *  Classe que gestiona diferents actuacions amb els usuaris de l'aplicació
 */
public class UserService {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Mètode que obtè el camp cliente_num d'un usuari amb "ROLE_USER"
     * @param username nom d'usuari de la taula users
     * @return
     */
    public static int getIDClient(String username) {
        Connection connection = ConexionBBDD.obreConnexioBBDD();
        String query = "SELECT cliente_num FROM user WHERE username = ?";
        PreparedStatement prepStmt = null;
        try {
            prepStmt = connection.prepareStatement(query);

            prepStmt.setString(1, username);

            ResultSet resultat = prepStmt.executeQuery();

            if (resultat.next()) {
                int cliente_num = resultat.getInt("cliente_num");
                return cliente_num;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                prepStmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ConexionBBDD.tancaConnexioBBDD(connection);
        }
        return 0;
    }


    /**
     * Mètode que inserta un usuari a la taula user
     * @param usuario Objecte de la classe Usuario
     * @throws SQLException
     */
    public static void insertaUsuario(Usuario usuario) throws SQLException {
        Connection connection = ConexionBBDD.obreConnexioBBDD();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        String username = usuario.getUsername();
        String password = passwordEncoder.encode((usuario.getPassword()));
        boolean enabled = true;
        Long id_cliente = usuario.getCliente_num();

        String query = "INSERT INTO user (cliente_num, enabled, password, username) VALUES (?,?,?,?);";

        PreparedStatement prepStmt = connection.prepareStatement(query);
        try {
            prepStmt.setLong(1, id_cliente);
            prepStmt.setBoolean(2, enabled);
            prepStmt.setString(3, password);
            prepStmt.setString(4, username);
            prepStmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        } finally {
            prepStmt.close();
            ConexionBBDD.tancaConnexioBBDD(connection);
        }
    }

    /**
     * Mètode que obté l'id de l'usuari emmagatzemat a la BBDD
     * @param usuario Objecte de la classe Usuario
     * @return id de l'usuari a la BBDD. De tipus Long
     */
    public static Long selectUserId(Usuario usuario) {
        Connection connection = ConexionBBDD.obreConnexioBBDD();
        String query = "SELECT id FROM user WHERE username = ?";
        PreparedStatement prepStmt = null;
        try {
            prepStmt = connection.prepareStatement(query);

            prepStmt.setString(1, usuario.getUsername());

            ResultSet resultat = prepStmt.executeQuery();

            if (resultat.next()) {
                Long id_usuario = resultat.getLong("id");
                return id_usuario;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                prepStmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ConexionBBDD.tancaConnexioBBDD(connection);
        }
        return null;
    }

    /**
     * Mètode que inserta els camps a la taula authoritie després de donar d'alta un usuari
     * @param usuario Objecte de la classe Usuario
     * @throws SQLException
     */
    public static void insertaAuth(Usuario usuario) throws SQLException {
        Connection connection = ConexionBBDD.obreConnexioBBDD();
        Long user_id = UserService.selectUserId(usuario);
        String auth = "ROLE_USER";


        String query = "INSERT INTO authoritie (authority, user_id) VALUES (?,?);";
        System.out.println(usuario);
        System.out.println(query);
        System.out.println(auth);
        System.out.println(user_id);

        PreparedStatement prepStmt = connection.prepareStatement(query);
        try {
            prepStmt.setString(1, auth);
            prepStmt.setLong(2, user_id);
            prepStmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());

        } finally {
            prepStmt.close();
            ConexionBBDD.tancaConnexioBBDD(connection);
        }
    }


}