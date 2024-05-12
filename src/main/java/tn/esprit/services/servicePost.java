package tn.esprit.services;


import javafx.collections.ObservableList;
import tn.esprit.entities.*;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class servicePost implements IService<Post> {

    private static Connection connection;

    public  servicePost(){
        connection = MyDataBase.getInstance().getConnection();
    }
    @Override
    public int ajouter(Post post) throws SQLException {

        String sql = "INSERT INTO post (title,created_at,author,views_count) VALUES (?, ?, ?,?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, post.getTitle());
        statement.setString(2, post.getCreated_at());
        statement.setString(3, post.getAuthor());
        statement.setInt(4, post.getViews_count());
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
    public void modifier(Post post) throws SQLException {

        String sql = "update post set title = ?, created_at= ?, author= ?,views_count=? where id_post = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, post.getTitle());
        preparedStatement.setString(2, post.getCreated_at());
        preparedStatement.setString(3, post.getAuthor());
        preparedStatement.setInt(4, post.getViews_count());
        preparedStatement.setInt(5,post.getId_Post());
        preparedStatement.executeUpdate();
        System.out.println("done" + post.getAuthor().toString());

    }

    @Override
    public void supprimer(int id_Post) throws SQLException {

        String sql = "delete from comment where post_id= ? ; " ;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement. setInt (1, id_Post);
        preparedStatement.executeUpdate();

        String sql1 = "delete from post where id_post= ? ; " ;
        PreparedStatement statement = connection.prepareStatement(sql1);
      statement. setInt (1, id_Post);
       statement.executeUpdate();


    }
    //String sql = "delete from comment where post_id= ? ; delete from Post where id_post=  ? ‘´ ;
    //PreparedStatement preparedStatement = connection.prepareStatement
    //preparedStatement. setInt (1, id_Post);
    //
    //preparedStatement. setInt (2, id_Post);

  //  SET FOREIGN_KEY_CHECKS=0 ;
    @Override
    public List<Post> recuperer() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String sql = "Select * from post";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            Post p = new Post();
            p.setId_Post(rs.getInt("id_Post"));
            p.setTitle(rs.getString("Title"));
            p.setCreated_at(rs.getString("created_at"));
            p.setAuthor(rs.getString("author"));
            p.setViews_count(rs.getInt("views_count"));

            posts.add(p);
        }
        return posts;
    }
    @Override
    public Post recupererPost(int id) throws SQLException {
        Post p = new Post();
        String sql = "SELECT * FROM post WHERE id_post = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {

                    p.setId_Post(rs.getInt("id_Post"));
                    p.setTitle(rs.getString("Title"));
                    p.setCreated_at(rs.getString("created_at"));
                    p.setAuthor(rs.getString("author"));
                    p.setViews_count(rs.getInt("views_count"));
                }
            }
        }
        return p;
    }
    @Override
    public List<Post> recupererComPost(int id_post) throws SQLException{
       return null ;
    }
    public React recupererReact(int react_id) throws SQLException {return null;
    };
    public static List<Post> search(String text) throws SQLException {
        String query = "SELECT * FROM post WHERE title LIKE ? OR author LIKE ?";
        List<Post> searchResults = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + text + "%");
            preparedStatement.setString(2, "%" + text + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Post post = new Post();
                    post.setId_Post(resultSet.getInt("Id_Post"));
                    post.setTitle(resultSet.getString("title"));
                    post.setAuthor(resultSet.getString("author"));
                    post.setCreated_at(resultSet.getString("created_at"));
                    post.setViews_count(resultSet.getInt("Views_count"));

                    searchResults.add(post);
                }
            }
        }
        return searchResults;
    }

    public void modifierReact(React react) throws SQLException{};
    public List<Comment> recupererReply(int id_parent){return null;}

    @Override
    public ObservableList<Post> afficher() throws SQLException {
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
}
