package tn.esprit.services;

import tn.esprit.entities.*;
import tn.esprit.services.IService;
import tn.esprit.utils.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupeService implements IService<Groupe> {
    private Connection connection;
    public GroupeService(){
         connection = MyConnection.getInstance().getCnx();
    }
    @Override
    public int ajouter(Groupe groupe) throws SQLException {
        String sql = "INSERT INTO `groupe`(`utilisateur_id`, `voyage_id`, `number_membre`) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, groupe.getUtilisateur_id());
        preparedStatement.setInt(2, groupe.getVoyage_id());
        preparedStatement.setInt(3, groupe.getNumber_membre());
        preparedStatement.executeUpdate(); // Remove the sql parameter from executeUpdate() method

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        int userId = -1; // Default value if no key is generated
        if (generatedKeys.next()) {
            userId = generatedKeys.getInt(1); // Assuming the ID is in the first column
        }

        return userId;
    }

    @Override
    public void modifier(Groupe groupe) throws SQLException {
        String sql = "UPDATE `groupe` SET `utilisateur_id`=?,`voyage_id`=?,`number_membre`=? WHERE id=?";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setInt(1,groupe.getUtilisateur_id());
        preparedStatement.setInt(2, groupe.getVoyage_id());
        preparedStatement.setInt(3,groupe.getNumber_membre());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public List<Groupe> recuperer() throws SQLException {
        return null;
    }

    @Override
    public List<Groupe> recupererComPost(int id_post) throws SQLException {
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
    public void supprimer(Groupe groupe) throws SQLException {
        String sql= "delete from groupe where id = ?";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setInt(1,groupe.getId());
        preparedStatement.executeUpdate();
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

    @Override
    public ObservableList<Groupe> afficher() throws SQLException {
        ObservableList<Groupe> groupes= FXCollections.observableArrayList();
        String sql = "select * from groupe";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            Groupe g = new Groupe();
            g.setId(rs.getInt("id"));
            g.setUtilisateur_id(rs.getInt("utilisateur_id"));
            g.setVoyage_id(rs.getInt("voyage_id"));
            g.setNumber_membre(rs.getInt("number_membre"));
            groupes.add(g);
        }
        return groupes;
    }

}

