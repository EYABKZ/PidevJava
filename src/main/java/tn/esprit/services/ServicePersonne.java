package tn.esprit.services;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tn.esprit.controllers.AfficherPControllers;
import tn.esprit.entities.*;
import tn.esprit.util.MyDataBase;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ServicePersonne implements IService<Personne> {

    private Connection connection;

    public  ServicePersonne(){
        connection = MyDataBase.getInstance().getConnection();
    }
    @Override
    public int ajouter(Personne personne) throws SQLException {

        String sql = "INSERT INTO user (lastname,email,password) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, personne.getLastname());
        statement.setString(2, personne.getEmail());
        statement.setString(3, personne.getPassword());
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
    public void modifier(Personne personne) throws SQLException {
        String sql = "update user set lastname = ?,email= ? , password= ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, personne.getLastname());
        preparedStatement.setString(2, personne.getEmail());
        preparedStatement.setString(3, personne.getPassword());
        preparedStatement.setInt(4,personne.getId());
        preparedStatement.executeUpdate();

    }

    @Override
    public void supprimer(int id) throws SQLException {

        String sql = "delete from user where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Personne> recuperer() throws SQLException {
        List<Personne> personnes = new ArrayList<>();
        String sql = "Select * from user";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            Personne p = new Personne();
            p.setId(rs.getInt("id"));
            p.setLastname(rs.getString("lastname"));
            p.setEmail(rs.getString("email"));
            p.setPassword(rs.getString("password"));


            personnes.add(p);
        }
        return personnes;
    }

    @Override
    public List<Personne> recupererComPost(int id_post) throws SQLException {
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
    public ObservableList<Personne> afficher() throws SQLException {
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


    public void login(String email,String pass) throws IOException {

        String query2="select * from user where email=?  and password=?";

        try{

            PreparedStatement preparedStatement = connection.prepareStatement(query2);

            preparedStatement.setString(1,email);
            preparedStatement.setString(2,pass);
            ResultSet rs= preparedStatement.executeQuery();
            Personne p;
            if(rs.next()){
                p=new Personne(rs.getInt("id"),rs.getString("lastname"),rs.getString("email"),rs.getString("password"));
                Personne.setCurrent_User(p);

                System.out.println(Personne.Current_User.getEmail());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Travel Me :: Success Message");
                alert.setHeaderText(null);
                alert.setContentText("Vous etes connecté");
                alert.showAndWait();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherP.fxml"));


                Parent root = loader.load();
                AfficherPControllers afficherPControllers = loader.getController();
                //afficherPControllers.setTxtAge(String.valueOf(p.getAge()));
                afficherPControllers.setTxtLastname(p.getLastname());
                afficherPControllers.setTxtEmail(p.getEmail());
                afficherPControllers.setTxtPassword(p.getPassword());

                afficherPControllers.setTxtId(String.valueOf(p.getId()));


                // Debug statement to check if root is loaded successfully
                System.out.println("FXML loaded successfully.");


                Scene scene;
                scene = new Scene(root);
                Stage stage = new Stage();

                stage.setScene(scene);

                stage.show();


            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Travel Me :: Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Wrong Email/Password !!");
                alert.showAndWait();
            }

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }


    public PreparedStatement sendPass() throws SQLException {
        System.out.println("cxcccccccccccccccccc");
        String query2="select * from user where email=? ";
        PreparedStatement smt = connection.prepareStatement(query2);
        return smt;
    }

    public String stat_count() throws SQLException {
        Statement statement = connection.createStatement();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM User");
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return String.valueOf(count);
            } else {
                throw new RuntimeException("No rows returned from the query.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}


