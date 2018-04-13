package view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import modals.CommentsModal;
import universite.com.parasite.R;

public class CommentsHolder extends RecyclerView.ViewHolder {

    private TextView u_name, comment_body;

    public CommentsHolder(View itemView) {
        super(itemView);

        u_name = itemView.findViewById(R.id.comment_uname);
        comment_body = itemView.findViewById(R.id.comment_body);

    }

    public void updateCommentUI(CommentsModal modal){
        u_name.setText(modal.getUsername());
        comment_body.setText(modal.getCommentBody());

    }
}
