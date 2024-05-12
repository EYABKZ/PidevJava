package tn.esprit.services;

import tn.esprit.utils.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VehiculeService {
    private Connection connection;


    public VehiculeService() {
        connection = MyConnection.getInstance().getCnx();
    }

    public int getVehiculeId(String transportModel) {
        String sql = "SELECT id_transport FROM moy_transport WHERE transport_model = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, transportModel);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_transport");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if not found or error occurs
    }

    public List<String> getAllVehicules() throws SQLException {
        List<String> vehicules = new ArrayList<>();
        String sql = "SELECT transport_model FROM moy_transport";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                vehicules.add(resultSet.getString("transport_model"));
            }
        }
        return vehicules;
    }
    public String getTransportModelById(int vehiculeId) {
        String transportModel = "";
        String sql = "SELECT transport_model FROM moy_transport WHERE id_transport = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, vehiculeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    transportModel = resultSet.getString("transport_model");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transportModel;
    }
}
