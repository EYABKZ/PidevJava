package tn.esprit.test;


import tn.esprit.entities.Post;
import tn.esprit.services.serviceComment;
import tn.esprit.services.servicePost;

import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {


        servicePost st = new servicePost();
       // serviceComment st = new serviceComment();

       /* try {
            st.ajouter(new Post("post", "neeeeeews", "Eyaaaa"));
            System.out.println("Ajouter");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        /*try {
            st.modifier(new Post(1,"iiii", conttt, "eya"));
            System.out.println("Modifier");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

//        try {
//            sp.supprimer(1);
//            System.out.println("Delete");
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

        try {
            System.out.println(st.recuperer());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}