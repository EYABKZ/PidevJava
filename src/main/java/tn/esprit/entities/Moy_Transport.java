package tn.esprit.entities;

import java.io.Serializable;

public class Moy_Transport implements Serializable {
    private int id_transport;
    private String Transport_Picture;
    private String Transport_Model;
    private  int Transport_Price;
    private String Transport_Description;
    private String Disponibility;


    public Moy_Transport() {
    }

    public Moy_Transport(int id_transport, String Transport_Picture, String Transport_Model, int Transport_Price, String Transport_Description, String Disponibility) {
        this.id_transport = id_transport;
        this.Transport_Picture = Transport_Picture;
        this.Transport_Model = Transport_Model;
        this.Transport_Price = Transport_Price;
        this.Transport_Description = Transport_Description;
        this.Disponibility = Disponibility;
    }

    public Moy_Transport(String Transport_Picture, String Transport_Model, int Transport_Price, String Transport_Description, String Disponibility) {
        this.Transport_Picture = Transport_Picture;
        this.Transport_Model = Transport_Model;
        this.Transport_Price = Transport_Price;
        this.Transport_Description = Transport_Description;
        this.Disponibility = Disponibility;
    }

    public int getId_transport() {
        return id_transport;
    }

    public void setId_transport(int id_transport) {
        this.id_transport = id_transport;
    }

    public String getTransport_Picture() {
        return Transport_Picture;
    }

    public void setTransport_Picture(String transport_Picture) {
        Transport_Picture = transport_Picture;
    }

    public String getTransport_Model() {
        return Transport_Model;
    }

    public void setTransport_Model(String transport_Model) {
        Transport_Model = transport_Model;
    }

    public int getTransport_Price() {
        return Transport_Price;
    }

    public void setTransport_Price(int transport_Price) {
        Transport_Price = transport_Price;
    }

    public String getTransport_Description() {
        return Transport_Description;
    }

    public void setTransport_Description(String transport_Description) {
        Transport_Description = transport_Description;
    }

    public String getDisponibility() {
        return Disponibility;
    }

    public void setDisponibility(String disponibility) {
        Disponibility = disponibility;
    }

   // @Override
   /* public String toString() {
        return "Transport{" +
                "id=" + id_transport +
                ", Transport_Picture='" + Transport_Picture + '\'' +
                ", Transport_Model='" + Transport_Model + '\'' +
                ", Prix='" + Transport_Price + '\'' +
                ", Transport_Description='" + Transport_Description + '\'' +
                ", Disponibility=" + Disponibility +
                '}';
    }*/

    @Override
    public String toString() {
        return this.getTransport_Model();
    }

}
