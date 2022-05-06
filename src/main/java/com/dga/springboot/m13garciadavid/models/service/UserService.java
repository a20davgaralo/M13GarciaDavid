package com.dga.springboot.m13garciadavid.models.service;

import com.dga.springboot.m13garciadavid.models.entity.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    public static int getIDClient(String username) {
        Connection connection = ConexionBBDD.obreConnexioBBDD();
        String query = "SELECT id_cliente FROM user WHERE username = ?";
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

    public static boolean insertaUsuario(Usuario usuario) throws SQLException {
        Connection connection = ConexionBBDD.obreConnexioBBDD();
            String username = usuario.getUsername();
            String password = usuario.getPassword();
            boolean enabled = true;
            int id_cliente = usuario.getId_cliente();

            String query = "INSERT INTO user (enabled, id_cliente, password, username) VALUES (?,?,?,?);";

            PreparedStatement prepStmt = connection.prepareStatement(query);
            try {
                prepStmt.setBoolean(1, enabled);
                prepStmt.setInt(2, id_cliente);
                prepStmt.setString(3, password);
                prepStmt.setString(4, username);
                prepStmt.executeUpdate();
                return true;
            } catch (SQLException ex) {
                System.out.println("Error " + ex.getMessage());
                return false;
            } finally {
                prepStmt.close();
                ConexionBBDD.tancaConnexioBBDD(connection);
            }
    }

}
