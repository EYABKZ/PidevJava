package tn.esprit.entities;

public class Comment {
    private int id_Comment;
    private int replies_count;
    private  String content;
    private String authorC;
    private Post post_id;
    private Comment parent_comment_id;
    private React reacts_id;



    public Comment()
    {}

    public Comment(int replies_count, String content, String authorC,Post post_id,Comment parent_comment_id,React reacts_id) {
        this.replies_count= replies_count;
        this.content= content;
        this.authorC = authorC;
        this.post_id = post_id;
        this.parent_comment_id = parent_comment_id;
        this.reacts_id = reacts_id;



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

    public int getReplies_count() {
        return replies_count;
    }

    public void setReplies_count(int replies_count) {
        this.replies_count= replies_count;
    }

    public String getAuthorC() {
        return authorC;
    }

    public void setAuthorC(String authorC) {
        this.authorC = authorC;
    }
    public Post getPost_id() {
        return post_id;
    }
    public void setPost_id(Post post_id) {
        this.post_id = post_id;
    }

    public Comment getParent_comment_id() {
        return parent_comment_id;
    }

    public void setParent_comment_id(Comment parent_comment_id) {
        this.parent_comment_id = parent_comment_id;
    }
    public React getReacts_id() {
        return reacts_id;
    }
    public void setReacts_id(React reacts_id) {
        this.reacts_id = reacts_id;
    }




    @Override
    public String toString() {
        return "{" +
                "id_comment=" + id_Comment +
                ", AuthorC='" + authorC + '\'' +
                ", content='" + content+ '\'' +
                ", replies_count=" + replies_count +'\''+
                ", post_id=" + post_id +'\''+
                ", reacts_id=" + reacts_id +'\''+
                ", parent_comment_id=" + parent_comment_id +

                '}';
    }
}


