package tn.esprit.services;


import javafx.collections.ObservableList;
import tn.esprit.entities.*;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMoy_Transport implements IService<Moy_Transport> {

    private Connection connection;

    public  ServiceMoy_Transport(){
        connection = MyDataBase.getInstance().getConnection();
    }
    @Override
    public int ajouter(Moy_Transport moy_transport) throws SQLException {
        String sql = "INSERT INTO moy_transport (Transport_Picture,Transport_Model, Transport_Price, Transport_Description, Disponibility) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, moy_transport.getTransport_Picture());
        statement.setString(2, moy_transport.getTransport_Model());
        statement.setInt(3, moy_transport.getTransport_Price());
        statement.setString(4, moy_transport.getTransport_Description());
        statement.setString(5, moy_transport.getDisponibility()); // Corrected line
        statement.executeUpdate();

        // Retrieve the auto-generated ID
        ResultSet generatedKeys = statement.getGeneratedKeys();
        int userId = -1; // Default value if no key is generated
        if (generatedKeys.next()) {
            userId = generatedKeys.getInt(1); // Assuming the ID is in the first column
        }
        return userId;
    }


    @Override
    public void modifier(Moy_Transport moy_transport) throws SQLException {
        String sql = "UPDATE moy_transport SET Transport_Picture = ?, Transport_Model = ?, Transport_Price = ?, Transport_Description = ?, Disponibility = ? WHERE id_Transport = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, moy_transport.getTransport_Picture());
        preparedStatement.setString(2, moy_transport.getTransport_Model());
        preparedStatement.setInt(3, moy_transport.getTransport_Price());
        preparedStatement.setString(4, moy_transport.getTransport_Description());
        preparedStatement.setString(5, moy_transport.getDisponibility());
        preparedStatement.setInt(6, moy_transport.getId_transport());
        preparedStatement.executeUpdate();
    }


    @Override
    public void supprimer(int id_transport) throws SQLException {

        String sql = "delete from moy_transport where id_transport = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id_transport);
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Moy_Transport> recuperer() throws SQLException {
        List<Moy_Transport> moy_transports = new ArrayList<>();
        String sql = "Select * from moy_transport";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            Moy_Transport t = new Moy_Transport();
            t.setId_transport(rs.getInt("id_transport"));
            t.setTransport_Picture(rs.getString("Transport_Picture"));
            t.setTransport_Model(rs.getString("Transport_Model"));
            t.setTransport_Price(rs.getInt("Transport_Price"));
            t.setTransport_Description(rs.getString("Transport_Description"));
            t.setDisponibility(rs.getString("Disponibility"));
            moy_transports.add(t);
        }
        return moy_transports;
    }

    @Override
    public List<Moy_Transport> recupererComPost(int id_post) throws SQLException {
        return null;
    }

    @Override
    public Post recupererPost(int id) throws SQLException {
        return null;
    }

    @Override
    public React recupererReact(int react_id) throws SQLException {
        return null;
    }

    @Override
    public void modifierReact(React react) throws SQLException {

    }

    @Override
    public List<Comment> recupererReply(int id_parent) {
        return null;
    }

    @Override
    public ObservableList<Moy_Transport> afficher() throws SQLException {
        return null;
    }

    @Override
    public void supprimer(Groupe groupe) throws SQLException {

    }

    @Override
    public void supprimer(Voyage voyage) throws SQLException {

    }
}
