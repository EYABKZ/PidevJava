package tn.esprit.services;


import tn.esprit.entities.Comment;
import tn.esprit.entities.Post;
import tn.esprit.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class serviceComment implements IService<Comment> {

    private Connection connection;

    public  serviceComment(){
        connection = MyDataBase.getInstance().getConnection();
    }
    @Override
    public int ajouter(Comment comment) throws SQLException {

        String sql = "INSERT INTO comment (Replies_Count,author_c,Content,post_id,signaler) VALUES (?, ?, ?,?,0)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, comment.getReplies_count());
        statement.setString(2, comment.getAuthorC());
        statement.setString(3, comment.getContent());
        statement.setInt(4, comment.getPost_id().getId_Post());
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
    public void modifier(Comment comment) throws SQLException {

        String sql = "update comment set Replies_count = ?, content = ?, author_c= ? where id_Comment = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, comment.getReplies_count());
        preparedStatement.setString(2, comment.getContent());
        preparedStatement.setString(3, comment.getAuthorC());
        preparedStatement.setInt(4,comment.getId_Comment());
        preparedStatement.executeUpdate();

    }

    @Override
    public void supprimer(int id_Comment) throws SQLException {

        String sql = "delete from comment where id_Comment = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id_Comment);
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Comment> recuperer() throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "Select * from comment";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            Comment c = new Comment();
            c.setId_Comment(rs.getInt("Id_Comment"));
            c.setReplies_count(rs.getInt("Replies_Count"));
            c.setContent(rs.getString("Content"));
            c.setAuthorC(rs.getString("author_c"));
            comments.add(c);
        }
        return comments;
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
    public List<Comment> recupererComPost(int id_post) throws SQLException{
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comment WHERE post_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_post);
            ResultSet rs = preparedStatement.executeQuery(); // Remove sql from executeQuery() call
            while (rs.next()){
                Comment c = new Comment();
                c.setId_Comment(rs.getInt("Id_Comment"));
                c.setReplies_count(rs.getInt("Replies_Count"));
                c.setContent(rs.getString("Content"));
                c.setAuthorC(rs.getString("author_c"));
                comments.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
}}
