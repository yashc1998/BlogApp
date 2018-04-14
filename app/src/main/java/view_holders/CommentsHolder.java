package view_holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import modals.CommentsModal;
import universite.com.parasite.R;
import universite.com.parasite.SharedPrefManager;

public class CommentsHolder extends RecyclerView.ViewHolder {

    private TextView u_name, comment_body;
    public CircleImageView commentorImage;


    public CommentsHolder(View itemView) {
        super(itemView);

        u_name = itemView.findViewById(R.id.comment_uname);
        comment_body = itemView.findViewById(R.id.comment_body);
        commentorImage = itemView.findViewById(R.id.commentor_image);

    }

    public void updateCommentUI(CommentsModal modal){
        Picasso.get()
                .load(modal.getUsere_pic())
                .placeholder(R.drawable.person_black)
                .error(R.drawable.person_black)
                .into(commentorImage);

        u_name.setText(modal.getUsername());
        comment_body.setText(modal.getCommentBody());
        comment_body.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
