package tn.esprit.entities;

public class Hebergement {
    private int id;
    private String lieu;
    private String description;

    public Hebergement(int id, String lieu, String description) {
        this.id = id;
        this.lieu = lieu;
        this.description = description;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Hebergement{" +
                "id=" + id +
                ", lieu='" + lieu + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
