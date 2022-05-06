package com.dga.springboot.m13garciadavid.models.service;

import com.dga.springboot.m13garciadavid.models.entity.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    public static int getIDClient(String username) {
        Connection connection = ConexionBBDD.obreConnexioBBDD();
        String query = "SELECT cliente_num FROM user WHERE username = ?";
        PreparedStatement prepStmt = null;
        try {
            prepStmt= connection.prepareStatement(query);

            prepStmt.setString(1, username);

            ResultSet resultat = prepStmt.executeQuery();

            if (resultat.next()) {
                int id_cliente = resultat.getInt("id_cliente");
                return id_cliente;
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

    public static void insertaUsuario(Usuario usuario) throws SQLException {
        Connection connection = ConexionBBDD.obreConnexioBBDD();
            String username = usuario.getUsername();
            String password = usuario.getPassword();
            boolean enabled = true;
            Long id_cliente = usuario.getCliente_num();

            String query = "INSERT INTO user (cliente_num, enabled, password, username) VALUES (?,?,?,?);";

            PreparedStatement prepStmt = connection.prepareStatement(query);
            try {
                prepStmt.setBoolean(1, enabled);
                prepStmt.setLong(2, id_cliente);
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

    public static Long selectUserId(Usuario usuario) {
        Connection connection = ConexionBBDD.obreConnexioBBDD();
        String query = "SELECT id FROM user WHERE username = ?";
        PreparedStatement prepStmt = null;
        try {
            prepStmt= connection.prepareStatement(query);

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
