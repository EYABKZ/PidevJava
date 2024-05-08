package tn.esprit.services;

import tn.esprit.entities.Accomodation;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IAccomodation implements IService<Accomodation>{

     private Connection connection;

     public IAccomodation() {connection = MyDataBase.getInstance().getConnection(); }


    @Override
    public void ajouter(Accomodation accomodation) throws SQLException {

         String sql= "INSERT INTO accomodation (  lieu,description) VALUES ('"+accomodation.getlieuaccomodation()+"','"+accomodation.getdescriptionaccomodation()+"')";
         Statement statement = connection.createStatement();
         statement.executeUpdate(sql);

    }

    @Override
    public void supprimer(int id) throws SQLException {

         String sql = "delete from accomodation where id = ?";
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
         preparedStatement.setInt(1,id);
         preparedStatement.executeUpdate();


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
    public ArrayList<Accomodation> lister() throws SQLException {

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


}