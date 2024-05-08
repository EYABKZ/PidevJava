package tn.esprit.services;



import tn.esprit.entities.Accomodation;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public interface IService <T>{
    void ajouter (T t) throws SQLException;
    void supprimer (int id) throws SQLException;
    void modifier (T t) throws SQLException;
    //List<Accomodation> lister () throws SQLException;
    T getById(int id) throws SQLException;
    ArrayList<T> lister() throws SQLException ;

 //   void ajouter_booking (T t) throws SQLException;
   // void supprimer_booking (int id) throws SQLException;
    //void modifier_booking (T t) throws SQLException;
    //List lister_booking () throws SQLException;

/*
    public interface IService<T>{
        void ajouter(T t);

        void modifier(T t);
        void supprimer(int t);


    }*/

}
