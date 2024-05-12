package tn.esprit.services;

import javafx.collections.ObservableList;
import tn.esprit.entities.*;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IBooking implements IService<Booking> {

    private Connection connection;

    public IBooking() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public int ajouter(Booking booking) throws SQLException {
        String sql = "INSERT INTO booking (debut, fin) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, new java.sql.Date(booking.getDebut().getTime()));
            preparedStatement.setDate(2, new java.sql.Date(booking.getFin().getTime()));
           // preparedStatement.setInt(3, booking.getUserId());
            //preparedStatement.setInt(4, booking.getAccomodationId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int userId = -1; // Default value if no key is generated
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1); // Assuming the ID is in the first column
            }

            return userId;
        }

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM booking WHERE id_booking = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Booking> recuperer() throws SQLException {
        return null;
    }

    @Override
    public List<Booking> recupererComPost(int id_post) throws SQLException {
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
    public ObservableList<Booking> afficher() throws SQLException {
        return null;
    }

    @Override
    public void supprimer(Groupe groupe) throws SQLException {

    }

    @Override
    public void supprimer(Voyage voyage) throws SQLException {

    }

    @Override
    public void modifier(Booking booking) throws SQLException {
        String sql = "UPDATE booking SET debut = ?, fin = ?  WHERE id_booking = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, new java.sql.Date(booking.getDebut().getTime()));
            preparedStatement.setDate(2,  new java.sql.Date(booking.getFin().getTime()));
          //  preparedStatement.setInt(3, booking.getUserId());
         //  preparedStatement.setInt(4, booking.getAccomodationId());
            preparedStatement.setInt(3, booking.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public ArrayList<Booking> lister() throws SQLException {
        ArrayList<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM booking";
        Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id_booking"));
                b.setDebut(rs.getDate("debut"));
                b.setFin(rs.getDate("fin"));
                b.setUserId(rs.getInt("user_id"));
                b.setAccomodationId(rs.getInt("accomodation_id"));
                bookings.add(b);
            }

        return bookings;
    }

    @Override
    public ArrayList<Accomodation> lister_acc() throws SQLException {
        return null;
    }

    @Override
    public Accomodation getById(int id) {
        return null;
    }


    public ArrayList<Booking> search(Date ffff) throws SQLException {
        ArrayList<Booking> bookings = new ArrayList<>();
        String sql = "Select * FROM booking WHERE debut LIKE CONCAT ('%', ?, '%')";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, ffff);
        ResultSet rs =statement.executeQuery(sql);
        while (rs.next()) {
            Booking b = new Booking();
            b.setId(rs.getInt("id_booking"));
            b.setDebut(rs.getDate("debut"));
            b.setFin(rs.getDate("fin"));
            b.setUserId(rs.getInt("user_id"));
            b.setAccomodationId(rs.getInt("accomodation_id"));
            bookings.add(b);
        }

        return bookings;
    }
}
