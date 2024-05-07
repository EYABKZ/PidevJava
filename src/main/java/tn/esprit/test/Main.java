package tn.esprit.test;

import tn.esprit.entities.Personne;
import tn.esprit.services.ServicePersonne;
import tn.esprit.util.MyDataBase;

import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {


        ServicePersonne sp = new ServicePersonne();

        try {
            sp.ajouter(new Personne("Ghayth","email","pass"));
            System.out.println("Ajouter");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            sp.modifier(new Personne(1,"Koussay","hh","hh"));
            System.out.println("Modifier");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

//        try {
//            sp.supprimer(1);
//            System.out.println("Delate");
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

        try {
            System.out.println(sp.recuperer());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}