package tn.esprit.services;


import tn.esprit.entities.Comment;
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

        String sql = "INSERT INTO comment (date,author,contentC) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, comment.getReplies_count());
        statement.setString(2, comment.getAuthorC());
        statement.setString(3, comment.getContent());
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

        String sql = "update comment set date = ?, contentC = ?, authorC= ? where idC = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, comment.getReplies_count());
        preparedStatement.setString(2, comment.getContent());
        preparedStatement.setString(3, comment.getAuthorC());
        preparedStatement.setInt(4,comment.getId_Comment());
        preparedStatement.executeUpdate();

    }

    @Override
    public void supprimer(int idC) throws SQLException {

        String sql = "delete from comment where idC = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idC);
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
            c.setId_Comment(rs.getInt("idC"));
            c.setReplies_count(rs.getString("date"));
            c.setContent(rs.getString("contentC"));
            c.setAuthorC(rs.getString("authorC"));
            comments.add(c);
        }
        return comments;
    }
}
