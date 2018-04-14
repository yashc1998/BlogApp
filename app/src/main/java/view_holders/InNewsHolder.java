package view_holders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import modals.PostModal;
import de.hdodenhof.circleimageview.CircleImageView;
import universite.com.parasite.Constants;
import acitivities.PerPostActivity;
import universite.com.parasite.R;

/**
 * Created by yashc on 22-03-2018.
 */

public class InNewsHolder extends RecyclerView.ViewHolder {

    CircleImageView creatorProfImage;
    ImageView creatorContentImage;
    TextView creatorName, creatorTime, creatorContentTitle, creatorContentText, totalComments;
    LinearLayout postsItemLayout;

    public InNewsHolder(View itemView) {
        super(itemView);

        postsItemLayout = itemView.findViewById(R.id.posts_item_layout);

        creatorProfImage = itemView.findViewById(R.id.creator_prof_image);
        creatorContentImage = itemView.findViewById(R.id.creator_content_image);

        creatorName = itemView.findViewById(R.id.creator_name);
        creatorTime = itemView.findViewById(R.id.creator_time);
        creatorContentTitle = itemView.findViewById(R.id.creator_content_title);
        creatorContentText = itemView.findViewById(R.id.creator_content_text);
        totalComments = itemView.findViewById(R.id.creator_post_total_comments);

    }

    public void updateInNewsUI(final PostModal modal, final Context context) {

//        Glide.with(context)
//                .load(modal.getmCreatorProfImage())
//                .into(creatorProfImage);

        Picasso.get()
                .load(modal.getmCreatorProfImage())
                .placeholder(R.drawable.ic_person_white_24dp)
                .into(creatorProfImage);

        if(modal.getmCreatorContentImage()!=null){
            for(String image : modal.getmCreatorContentImage()){
//                Glide.with(context).load(Constants.HOST_URL + "/images/postimg/"+image).apply(new RequestOptions().dontAnimate()).into(creatorContentImage);
                Picasso.get().load(Constants.HOST_URL + "/images/postimg/"+image).placeholder(R.drawable.bg).resize(600, 0).into(creatorContentImage);
                creatorContentImage.setVisibility(View.VISIBLE);
            }
        }

        creatorName.setText(modal.getmCreatorName());
        creatorTime.setText(modal.getmCreatorTime());
        creatorContentTitle.setText(modal.getmPostTitle());
        creatorContentText.setText(Html.fromHtml(modal.getmCreatorContentText()));
        creatorContentText.setMovementMethod(LinkMovementMethod.getInstance());
        totalComments.setText(modal.getmTotalComments());


        postsItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PerPostActivity.class);
                intent.putExtra("POST_ID", modal.getmPostID());
                intent.putExtra("TITLE", modal.getmPostTitle());
                intent.putExtra("BODY", modal.getmCreatorContentText());
                intent.putExtra("IMAGES", modal.getmCreatorContentImage());
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

            }
        });
    }

    public String timeDiff(long start, long end){

        long diff = end - start;
        String date = "";

        if(diff < 60 && diff > 0){
            date = (diff/60) + "m ago";
        }else if (diff >60 && diff < 2592000){
            date =  (diff/(60*24)) + " days ago";
        }else if (diff>=2592000){
            date =  (diff/(60*24*30)) + " months ago";
        }

        return date;
    }
}
