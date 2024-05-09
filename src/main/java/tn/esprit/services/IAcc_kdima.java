/*
package tn.esprit.services;

import tn.esprit.utils.MyDataBase;
import tn.esprit.entities.accomodation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public abstract class IAccomodation  implements IService<accomodation> {
    private Connection cnx;

    public IAccomodation() {
        cnx = MyDataBase.getInstance().getConnection();
    }


    public void ajouter_accomodation(accomodation a) {
        try {
            String requete1 = "INSERT INTO accomodation(id,lieu,description) VALUES(?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(requete1);
            pst.setString(1, a.getidaccomodation());
            pst.setString(2, a.getlieuaccomodation());
            pst.setString(3, a.getdescriptionaccomodation());

            pst.executeUpdate();
            System.out.println("accomodation ajouté !");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }


    public void supprimer_accomodaion(String idaccomodation) {

        try {
            String requete2 = "DELETE FROM accomodation WHERE idaccomodation=" + idaccomodation;
            Statement st = cnx.createStatement();

            st.executeUpdate(requete2);
            System.out.println("accomodation supprimé !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }


    public ArrayList<accomodation> recuperer_accomodations() {
        ArrayList<accomodation> myList = new ArrayList<>();
        try {

            String requete3 = "Select * FROM accomodation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete3);
            while (rs.next()) {
                accomodation accomodation = new accomodation();

                accomodation.setidaccomodation(rs.getString(1));
                accomodation.setlieuaccomodation(rs.getString("lieuaccomodation"));
                accomodation.setdescriptionaccomodation(rs.getString("descriptionaccomodation"));

                myList.add(accomodation);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        System.out.println("Affichage executé");
        return myList;


    }




    public void modifier_accomodation(String idaccomodation,String lieuaccomodation,String descriptionaccomodation) {
        try {
            String requete4 = " UPDATE accomodation SET " + "  lieuaccomodation= ? , descriptionaccomodation  = ? WHERE idaccomodation= "+ idaccomodation;
            PreparedStatement pst =cnx.prepareStatement(requete4);
            pst.setString(1, lieuaccomodation);
            pst.setString(2, descriptionaccomodation);

            pst.executeUpdate();
            System.out.println("accomodation modifié !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }




    public accomodation getaccomodationById(String idaccomodation) {
        accomodation accomodation = null;

        String requete5 = "SELECT idaccomodation, lieuaccomodation, descriptionaccomodation FROM accomodation WHERE idaccomodation = ?";

        try {
            PreparedStatement ps = cnx.prepareStatement(requete5);
            ps.setString(1, idaccomodation);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                accomodation = new accomodation();

                accomodation.setidaccomodation(rs.getString("idaccomodation"));
                accomodation.setlieuaccomodation(rs.getString("lieuaccomodation"));
                accomodation.setdescriptionaccomodation(rs.getString("descriptionaccomodation"));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return accomodation;
    }


    public abstract void ajouter(accomodation accomodation);
}

 */