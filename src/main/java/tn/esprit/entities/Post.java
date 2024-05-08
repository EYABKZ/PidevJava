package tn.esprit.entities;

public class Post {
    private int id_Post;
    private String title;
    private  String created_at;
    private String author;
    private int views_count;


    public Post() {}

    public Post(String title, String created_at, String author, int  views_count) {
        this.title = title;
        this.created_at= created_at;
        this.author = author;
        this.views_count= views_count;

    }

    public int getId_Post() {
        return id_Post;
    }

    public void setId_Post(int id_Post) {
        this.id_Post = id_Post;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public int getViews_count() {
        return views_count;
    }

    public void setViews_count(int views_count) {
        this.views_count= views_count;
    }


    @Override
    public String toString() {
        return "post{" +
                "id_Post=" + id_Post +
                ", title='" + title + '\'' +
                ", created_at='" + created_at+ '\'' +
                ", views_count='" + views_count+ '\'' +
                ", author=" + author +
                '}';
    }


}
