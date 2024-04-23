package tn.esprit.services;

import tn.esprit.entities.Post;

import java.sql.SQLException;
import java.util.List;

public interface IService <T>{

    int ajouter (T t) throws SQLException;

    void modifier (T t) throws SQLException;

    void supprimer(int id) throws SQLException;

    List<T> recuperer() throws SQLException;
    List<T> recupererComPost(int id_post) throws SQLException;
    Post recupererPost(int id) throws SQLException;

}
