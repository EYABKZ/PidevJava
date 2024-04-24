package tn.esprit.test;


import tn.esprit.entities.Moy_Transport;
import tn.esprit.services.ServiceMoy_Transport;

import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {


        ServiceMoy_Transport st = new ServiceMoy_Transport();

       /* try {
            st.ajouter(new Moy_Transport());
            System.out.println("Ajouter");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

        /*try {
            st.modifier(new Transport(1,"Ford", 1000, "Favorite Car"));
            System.out.println("Modifier");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

//        try {
//            sp.supprimer(1);
//            System.out.println("Delate");
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