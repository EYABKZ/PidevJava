package tn.esprit.services;

import tn.esprit.entities.Booking;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;

public class IBooking implements IService<Booking> {

    private Connection connection;

    public IBooking() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Booking booking) throws SQLException {
        String sql = "INSERT INTO booking (debut, fin) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, new java.sql.Date(booking.getDebut().getTime()));
            preparedStatement.setDate(2, new java.sql.Date(booking.getFin().getTime()));
           // preparedStatement.setInt(3, booking.getUserId());
            //preparedStatement.setInt(4, booking.getAccomodationId());
            preparedStatement.executeUpdate();
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
    public Booking getById(int id) throws SQLException {
        String sql = "SELECT * FROM booking WHERE id_booking = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Booking booking = new Booking();
                    booking.setId(resultSet.getInt("id_booking"));
                    booking.setDebut(resultSet.getDate("debut"));
                    booking.setFin(resultSet.getDate("fin"));
                    booking.setUserId(resultSet.getInt("user_id"));
                    booking.setAccomodationId(resultSet.getInt("accomodation_id"));
                    return booking;
                }
            }
        }
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
