/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.prefs.Preferences;



/**
 *
 * @author MSI GF63
 */
public final class SessionManager {
 
    private static SessionManager instance;
 
     private static int id;
    private static String nom;
    private static String prenom;
    private static int Age;
    private static String Email;
    private static String Password;


   
  //SessionManager.getInstace(rs.getInt("id"),rs.getInt("cin"),rs.getString("user_name"),rs.getInt("numero"),rs.getString("email"),rs.getString("adresse"),rs.getString("roles"));
    private SessionManager(int id , String nom ,String prenom, int age , String email ,String password ) {
    SessionManager.id=id;
    SessionManager.nom=nom;
    SessionManager.prenom=prenom;
    SessionManager.Age=age;
    SessionManager.Email=email;
    SessionManager.Password=password;

    }
 
    /**
    *
    * @param nom
    * @param id
    * @return
    */
    public static SessionManager getInstace(int id , String nom ,String prenom, int age , String email ,String password) {
        if(instance == null) {
            instance = new SessionManager( id , nom ,  prenom ,  age,  email ,password);
        }
        return instance;
    }

    public static SessionManager getInstance() {
        return instance;
    }

    public static void setInstance(SessionManager instance) {
        SessionManager.instance = instance;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        SessionManager.id = id;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        this.Age = age;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

   
    
    public static void cleanUserSession() {
    id=0;
    nom="";
     prenom="";
     Age=0;
     Email="";
     Password="";

    }
 
    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + Age +
                ", email='" + Email + '\'' +
                ", password='" + Password + '\'' +
                '}';
    }
 
    
    static class cleanUserSession {
 
        public cleanUserSession() {
            id=0;
            nom="";
            prenom="";
            Age=0;
            Email="";
            Password="";
        }
    }
}