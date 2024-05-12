package tn.esprit.services;

import javafx.collections.ObservableList;
import tn.esprit.entities.*;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IAccomodation implements IService<Accomodation>{

     private Connection connection;

     public IAccomodation() {connection = MyDataBase.getInstance().getConnection(); }


    @Override
    public int ajouter(Accomodation accomodation) throws SQLException {

         String sql= "INSERT INTO accomodation (  lieu,description) VALUES ('"+accomodation.getlieuaccomodation()+"','"+accomodation.getdescriptionaccomodation()+"')";
         Statement statement = connection.createStatement();
         statement.executeUpdate(sql);
        ResultSet generatedKeys = statement.getGeneratedKeys();
        int userId = -1; // Default value if no key is generated
        if (generatedKeys.next()) {
            userId = generatedKeys.getInt(1); // Assuming the ID is in the first column
        }

        return userId;

    }

    @Override
    public void supprimer(int id) throws SQLException {

         String sql = "delete from accomodation where id = ?";
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
         preparedStatement.setInt(1,id);
         preparedStatement.executeUpdate();


    }

    @Override
    public List<Accomodation> recuperer() throws SQLException {
        return null;
    }

    @Override
    public List<Accomodation> recupererComPost(int id_post) throws SQLException {
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
    public ObservableList<Accomodation> afficher() throws SQLException {
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
    public void modifier(Accomodation accomodation) throws SQLException {

         String sql = "UPDATE accomodation set lieu =? , description =? where id =?";
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
         preparedStatement.setString(1,accomodation.getlieuaccomodation());
        preparedStatement.setString(2,accomodation.getdescriptionaccomodation());
        preparedStatement.setString(3,accomodation.getidaccomodation());
        preparedStatement.executeUpdate();



    }

    @Override
    public ArrayList<Accomodation> lister_acc() throws SQLException {

         ArrayList<Accomodation> accomodations = new ArrayList<>();
         String sql ="Select * FROM accomodation";
         Statement statement = connection.createStatement();
         ResultSet rs =statement.executeQuery(sql);
         while (rs.next()){
             Accomodation a=new Accomodation();
             a.setidaccomodation(rs.getString("id"));
             a.setlieuaccomodation(rs.getString("lieu"));
             a.setdescriptionaccomodation(rs.getString("description"));
             accomodations.add(a);
         }


        return accomodations;
    }

    @Override
    public Accomodation getById(int id) {
        return null;
    }

    public ArrayList<Accomodation> rech(String ffff) throws SQLException {
        String sql = "Select * FROM accomodation WHERE lieu LIKE CONCAT ('%', ?, '%')";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, ffff);
        ResultSet rs =statement.executeQuery(sql);
        ArrayList<Accomodation> accomodations = new ArrayList<>();
        while (rs.next()){
            Accomodation a=new Accomodation();
            a.setidaccomodation(rs.getString("id"));
            a.setlieuaccomodation(rs.getString("lieu"));
            a.setdescriptionaccomodation(rs.getString("description"));
            accomodations.add(a);
        }


        return accomodations;
    }



}