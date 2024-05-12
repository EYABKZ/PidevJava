package tn.esprit.services;


import javafx.collections.ObservableList;
import tn.esprit.entities.*;
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

        String reactSql = "INSERT INTO react (likes,dislike,userlike,userdislike) VALUES (0,0,null,null)";
        PreparedStatement state = connection.prepareStatement(reactSql, Statement.RETURN_GENERATED_KEYS);
        state.executeUpdate();
        // Retrieve the auto-generated ID
        ResultSet generatedReactKey = state.getGeneratedKeys();
        int reactId = -1; // Default value if no key is generated
        if (generatedReactKey.next()) {
            reactId = generatedReactKey.getInt(1); // Assuming the ID is in the first column
        }


        String sql = "INSERT INTO comment (Replies_Count,author_c,Content,post_id,reacts_id,signaler,parent_comment_id) VALUES (?, ?, ?,?,?,0,?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, comment.getReplies_count());
        statement.setString(2, comment.getAuthorC());
        statement.setString(3, comment.getContent());
        statement.setInt(4, comment.getPost_id().getId_Post());
        statement.setInt(5, reactId);
        if (comment.getParent_comment_id() != null) {
            statement.setInt(6, comment.getParent_comment_id().getId_Comment());
        } else {
            statement.setNull(6, java.sql.Types.INTEGER);
        }        statement.executeUpdate();

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

        String sql = " delete from comment where id_Comment = ?";
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
                React r=new React();
                r=recupererReact(rs.getInt("reacts_id"));
                c.setReacts_id(r);
                comments.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
}
    @Override
    public React recupererReact(int react_id) throws SQLException{
        String sql = "SELECT * FROM react WHERE id = ?";
        React r = new React();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, react_id);
            ResultSet rs = preparedStatement.executeQuery(); // Remove sql from executeQuery() call
            while (rs.next()){
                r.setlikes(rs.getInt("likes"));
                r.setDislike(rs.getInt("dislike"));
                r.setUser_like(rs.getString("userlike"));
                r.setUser_dislike(rs.getString("userdislike"));
                r.setId(react_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }
    @Override
    public void modifierReact(React react) throws SQLException {

        String sql = "update react set likes = ?, dislike = ?, userlike= ?, userdislike = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, react.getlikes());
        preparedStatement.setInt(2, react.getDislike());
        preparedStatement.setString(3, react.getUser_like());
        preparedStatement.setString(4,react.getUser_dislike());
        preparedStatement.setInt(5,react.getId());
        preparedStatement.executeUpdate();

    }
    @Override
    public List<Comment> recupererReply(int id_parent) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comment WHERE parent_comment_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_parent);
            ResultSet rs = preparedStatement.executeQuery(); // Remove sql from executeQuery() call
            while (rs.next()){
                Comment c = new Comment();
                c.setId_Comment(rs.getInt("Id_Comment"));
                c.setReplies_count(rs.getInt("Replies_Count"));
                c.setContent(rs.getString("Content"));
                c.setAuthorC(rs.getString("author_c"));
                React r=new React();
                r=recupererReact(rs.getInt("reacts_id"));
                c.setReacts_id(r);
                comments.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }

    @Override
    public ObservableList<Comment> afficher() throws SQLException {
        return null;
    }

    @Override
    public void supprimer(Groupe groupe) throws SQLException {

    }

    @Override
    public void supprimer(Voyage voyage) throws SQLException {

    }

}
