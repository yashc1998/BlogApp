package modals;

import static universite.com.parasite.Constants.BASE_URL;
import static universite.com.parasite.Constants.HOST_URL;

public class CommentsModal {

    private String username, commentId, commentBody, commentDate;
    private String usere_pic;

    public CommentsModal(String username, String commentId, String commentBody, String commentDate, String usere_pic) {
        this.username = username;
        this.commentId = commentId;
        this.commentBody = commentBody;
        this.commentDate = commentDate;
        this.usere_pic = usere_pic;
    }

    public String getUsere_pic() {
        return HOST_URL + "/images/" + usere_pic;
    }

    public String getUsername() {
        return username;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public String getCommentDate() {
        return commentDate;
    }
}
