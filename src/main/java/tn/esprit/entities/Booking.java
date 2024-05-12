package tn.esprit.entities;

import java.util.Date;

public class Booking {
    private int id;
    private Date debut;
    private Date fin;
    private int userId;
    private int accomodationId;

    public Booking() {}

    public Booking(int id ,Date debut, Date fin, int userId, int accomodationId) {
        this.id = id;
        this.debut = debut;
        this.fin = fin;
        this.userId = userId;
        this.accomodationId = accomodationId;
    }
    public Booking(Date debut, Date fin, int userId, int accomodationId) {
        this.debut = debut;
        this.fin = fin;
       this.userId = userId;
       this.accomodationId = accomodationId;
    }



    public Booking(int id,Date debut, Date fin) {
        this.id= id ;
        this.debut = debut;
        this.fin = fin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAccomodationId() {
        return accomodationId;
    }

    public void setAccomodationId(int accomodationId) {
        this.accomodationId = accomodationId;
    }

    @Override
    public String toString() {
        return "booking{" +
                "id_booking=" + id +
                ", debut=" + debut +
                ", fin=" + fin +
                ", user_id=" + userId +
                ", accomodation_id=" + accomodationId +
                '}';
    }
}
