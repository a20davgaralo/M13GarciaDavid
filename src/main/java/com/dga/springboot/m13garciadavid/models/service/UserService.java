package com.dga.springboot.m13garciadavid.models.service;

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

    public static boolean insertaUsuario(String username, String password, int id_cliente) {




        return false;
    }

}
