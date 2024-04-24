package tn.esprit.entities;

public class Personne {

    private int id;
    private  String lastname;
    private String email;
    private String password;

    public static Personne Current_User;


    public Personne() {
    }

    public Personne(int id, String lastname,String email ,String password) {
        this.id = id;
        this.lastname = lastname;
        this.email=email;
        this.password=password;
    }

    public Personne(String lastname,String email ,String password) {
        this.lastname = lastname;
        this.email=email;
        this.password=password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Personne getCurrent_User() {
        return Current_User;
    }

    public static void setCurrent_User(Personne Current_User) {
        Personne.Current_User = Current_User;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
