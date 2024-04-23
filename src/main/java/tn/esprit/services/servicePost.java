package tn.esprit.services;


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

    }

    @Override
    public void supprimer(int id_Post) throws SQLException {

        String sql = "delete from post where id_Post = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id_Post);
        preparedStatement.executeUpdate();

    }

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
}
