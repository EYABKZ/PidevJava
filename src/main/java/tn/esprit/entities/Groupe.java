package tn.esprit.entities;

import java.sql.Date;

public class Groupe {
    private int id;
    private int utilisateur_id;
    private int voyage_id;
    private int number_membre;

    public Groupe() {
    }

    public Groupe(int utilisateur_id, int voyage_id, int number_membre) {
        this.utilisateur_id = utilisateur_id;
        this.voyage_id = voyage_id;
        this.number_membre = number_membre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public int getVoyage_id() {
        return voyage_id;
    }

    public void setVoyage_id(int voyage_id) {
        this.voyage_id = voyage_id;
    }

    public int getNumber_membre() {
        return number_membre;
    }

    public void setNumber_membre(int number_membre) {
        this.number_membre = number_membre;
    }

    @Override
    public String toString() {
        return "Groupe{" +
                "id=" + id +
                ", utilisateur_id=" + utilisateur_id +
                ", voyage_id=" + voyage_id +
                ", number_membre=" + number_membre +
                '}';
    }
}

