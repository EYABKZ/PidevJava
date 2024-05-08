
package tn.esprit.entities;

public class React {
    private int id;
    private int likes;
    private  int dislike;
    private String user_like;
    private String user_dislike;


    public React() {}

    public React(int likes, int dislike, String user_like, String user_dislike) {
        this.likes = likes;
        this.dislike= dislike;
        this.user_like = user_like;
        this.user_dislike=user_dislike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getlikes() {
        return likes ;
    }

    public void setlikes(int likes) {
        this.likes = likes;
    }

    public int getDislike() {
        return dislike ;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public String getUser_like() {
        return user_like;
    }

    public void setUser_like(String user_like) {
        this.user_like = user_like;
    }

    public String getUser_dislike() {
        return user_dislike;
    }

    public void setUser_dislike(String user_dislike) {
        this.user_dislike = user_dislike;
    }



    @Override
    public String toString() {
        return "react{" +
                "id=" + id +
                ", likes='" + likes + '\'' +
                ", dislike='" + dislike+ '\'' +
                ", user_like='" +user_like+ '\'' +
                ", user_dislike=" + user_dislike +
                '}';
    }


}