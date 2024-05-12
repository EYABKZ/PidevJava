package tn.esprit.entities;

import java.sql.Date;

public class Voyage {
    private int id;
    private int vehicule_id;
    private int hebergement_id;
    private int evenement_id;
    private Date depart;
    private Date arrivee;
    private int utilisateur_id;

    public Voyage() {
    }

    public Voyage(int vehicule_id, int hebergement_id, int evenement_id, Date depart, Date arrivee, int utilisateur_id) {
        this.vehicule_id = vehicule_id;
        this.hebergement_id = hebergement_id;
        this.evenement_id = evenement_id;
        this.depart = depart;
        this.arrivee = arrivee;
        this.utilisateur_id = utilisateur_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehicule_id() {
        return vehicule_id;
    }

    public void setVehicule_id(int vehicule_id) {
        this.vehicule_id = vehicule_id;
    }

    public int getHebergement_id() {
        return hebergement_id;
    }

    public void setHebergement_id(int hebergement_id) {
        this.hebergement_id = hebergement_id;
    }

    public int getEvenement_id() {
        return evenement_id;
    }

    public void setEvenement_id(int evenement_id) {
        this.evenement_id = evenement_id;
    }

    public Date getDepart() {
        return depart;
    }

    public void setDepart(Date depart) {
        this.depart = depart;
    }

    public Date getArrivee() {
        return arrivee;
    }

    public void setArrivee(Date arrivee) {
        this.arrivee = arrivee;
    }

    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    @Override
    public String toString() {
        return "Voyage{" +
                "id=" + id +
                ", vehicule_id=" + vehicule_id +
                ", hebergement_id=" + hebergement_id +
                ", evenement_id=" + evenement_id +
                ", depart=" + depart +
                ", arrivee=" + arrivee +
                ", utilisateur_id=" + utilisateur_id +
                '}';
    }
}
