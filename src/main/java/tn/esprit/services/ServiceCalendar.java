package tn.esprit.services;


import javafx.collections.ObservableList;
import tn.esprit.entities.*;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCalendar implements IService<Calendar> {

    private Connection connection;

    public  ServiceCalendar(){
        connection = MyDataBase.getInstance().getConnection();
    }
    @Override
    public int ajouter(Calendar calendar) throws SQLException {
        String sql = "INSERT INTO calendar (Title,Start, End, Description, All_Day, Background_Color, Border_Color, Text_Color, transport_id, Passenger_Count) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, calendar.getTitle());
        statement.setString(2, calendar.getStart());
        statement.setString(3, calendar.getEnd());
        statement.setString(4, calendar.getDescription());
        statement.setInt(5, calendar.getAll_Day());
        statement.setString(6, calendar.getBackground_Color());
        statement.setString(7, calendar.getBorder_Color());
        statement.setString(8, calendar.getText_Color());
        statement.setInt(9, calendar.getId_transport().getId_transport());
        statement.setInt(10, calendar.getPassenger_Count());
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
    public void modifier(Calendar calendar) throws SQLException {
        String sql = "UPDATE calendar SET Title = ?, Start = ?, End = ?, Description = ?, All_Day = ?, Background_Color = ?, Border_Color = ?, Text_Color = ?, transport_id = ?, Passenger_Count = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, calendar.getTitle());
        preparedStatement.setString(2, calendar.getStart());
        preparedStatement.setString(3, calendar.getEnd());
        preparedStatement.setString(4, calendar.getDescription());
        preparedStatement.setInt(5, calendar.getAll_Day());
        preparedStatement.setString(6, calendar.getBackground_Color());
        preparedStatement.setString(7, calendar.getBorder_Color());
        preparedStatement.setString(8, calendar.getText_Color());
        preparedStatement.setObject(9, calendar.getId_transport().getId_transport());
        preparedStatement.setInt(10, calendar.getPassenger_Count());
        preparedStatement.setInt(11, calendar.getId());
        preparedStatement.executeUpdate();
    }


    @Override
    public void supprimer(int id) throws SQLException {

        String sql = "delete from calendar where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Calendar> recuperer() throws SQLException {
        List<Calendar> calendars = new ArrayList<>();
        String sql = "SELECT * FROM calendar INNER JOIN Moy_Transport ON calendar.transport_id = Moy_Transport.id_transport";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            Calendar t = new Calendar();
            t.setId(rs.getInt("id"));
            t.setTitle(rs.getString("Title"));
            t.setStart(rs.getString("Start"));
            t.setEnd(rs.getString("End"));
            t.setDescription(rs.getString("Description"));
            t.setAll_Day(rs.getInt("All_Day"));
            t.setBackground_Color(rs.getString("Background_Color"));
            t.setBorder_Color(rs.getString("Border_Color"));
            t.setText_Color(rs.getString("Text_Color"));
            t.setId_transport(new Moy_Transport(rs.getInt("transport_id"), rs.getString("Transport_Picture"), rs.getString("Transport_Model"), rs.getInt("Transport_Price"), rs.getString("Transport_Description"), rs.getString("Disponibility")));
            t.setPassenger_Count(rs.getInt("Passenger_Count"));
            calendars.add(t);
        }
        return calendars;
    }

    @Override
    public List<Calendar> recupererComPost(int id_post) throws SQLException {
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
    public ObservableList<Calendar> afficher() throws SQLException {
        return null;
    }

    @Override
    public void supprimer(Groupe groupe) throws SQLException {

    }

    @Override
    public void supprimer(Voyage voyage) throws SQLException {

    }

    @Override
    public ArrayList<Booking> lister() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Accomodation> lister_acc() throws SQLException {
        return null;
    }

    @Override
    public Accomodation getById(int id) {
        return null;
    }

    public List<Moy_Transport> recupererTousLesTransports() throws SQLException {
        List<Moy_Transport> transports = new ArrayList<>();
        String sql = "SELECT * FROM Moy_Transport";
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
            transports.add(t);
        }
        return transports;
    }
}
