package tn.esprit.services;

import tn.esprit.utils.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurService {
    private Connection connection;

    public UtilisateurService() {
        connection = MyConnection.getInstance().getCnx();
    }

    public List<String> getAllUsers() throws SQLException {
        List<String> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM user"; // Assuming your user table has a column named "id" for the user's id
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                utilisateurs.add(resultSet.getString("id"));
            }
        }
        return utilisateurs;
    }

    public int getUserId(String userName) throws SQLException {
        int userId = -1;
        String query = "SELECT id FROM user WHERE nom = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt("id");
            }
        }
        return userId;
    }
}
