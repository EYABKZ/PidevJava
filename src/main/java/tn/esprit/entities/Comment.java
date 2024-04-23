package tn.esprit.entities;

public class Comment {
    private int id_Comment;
    private String replies_count;
    private  String content;
    private String authorC;



    public Comment()
    {
        this.id_Comment= id_Comment;
        this.replies_count= replies_count;
        this.content= content;
        this.authorC = authorC;
    }

    public Comment(String replies_count, String content, String authorC) {
        this.replies_count= replies_count;
        this.content= content;
        this.authorC = authorC;
    }

    public int getId_Comment() {
        return id_Comment;
    }

    public void setId_Comment(int id_Comment) {
        this.id_Comment = id_Comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content= content;
    }

    public String getReplies_count() {
        return replies_count;
    }

    public void setReplies_count(String replies_count) {
        this.replies_count= replies_count;
    }

    public String getAuthorC() {
        return authorC;
    }

    public void setAuthorC(String authorC) {
        this.authorC = authorC;
    }

    @Override
    public String toString() {
        return "{" +
                "id_comment=" + id_Comment +
                ", AuthorC='" + authorC + '\'' +
                ", content='" + content+ '\'' +
                ", replies_count=" + replies_count +
                '}';
    }
}


