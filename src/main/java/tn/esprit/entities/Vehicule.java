package tn.esprit.entities;

public class Vehicule {
    private int id;
    private String typeVehicule;

    public Vehicule(int id, String typeVehicule) {
        this.id = id;
        this.typeVehicule = typeVehicule;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeVehicule() {
        return typeVehicule;
    }

    public void setTypeVehicule(String typeVehicule) {
        this.typeVehicule = typeVehicule;
    }

    @Override
    public String toString() {
        return "Vehicule{" +
                "id=" + id +
                ", typeVehicule='" + typeVehicule + '\'' +
                '}';
    }
}
