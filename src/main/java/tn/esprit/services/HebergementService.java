package tn.esprit.services;

import tn.esprit.utils.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class HebergementService {
    private Connection connection;

    public HebergementService() {
        connection = MyConnection.getInstance().getCnx();
    }

    public int getHebergementId(String hebergementName) {
        String sql = "SELECT id FROM accomodation WHERE lieu = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, hebergementName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if not found or error occurs
    }
    public List<String> getAllHebergements() throws SQLException {
        List<String> hebergements = new ArrayList<>();
        String sql = "SELECT lieu FROM accomodation";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                hebergements.add(resultSet.getString("lieu"));
            }
        }
        return hebergements;
    }
    public String getHebergementLieuById(int hebergementId) {
        String lieu = "";
        String sql = "SELECT lieu FROM accomodation WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, hebergementId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    lieu = resultSet.getString("lieu");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lieu;
    }

}
