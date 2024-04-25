package tn.esprit.services;


import tn.esprit.entities.Comment;
import tn.esprit.entities.Post;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class servicePost implements IService<Post> {

    private Connection connection;

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

        String sql = "update post set title = ?, created_at= ?, author= ?,views_count=? where id_Post = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, post.getTitle());
        preparedStatement.setString(2, post.getCreated_at());
        preparedStatement.setString(3, post.getAuthor());
        preparedStatement.setInt(4,post.getId_Post());
        preparedStatement.setInt(5, post.getViews_count());
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
        Post p = null;
        String sql = "SELECT * FROM post WHERE id = ?";
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
}
