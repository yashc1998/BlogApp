package modals;

public class CommentsModal {

    private String username, commentId, commentBody, commentDate;

    public CommentsModal(String username, String commentId, String commentBody, String commentDate) {
        this.username = username;
        this.commentId = commentId;
        this.commentBody = commentBody;
        this.commentDate = commentDate;
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
