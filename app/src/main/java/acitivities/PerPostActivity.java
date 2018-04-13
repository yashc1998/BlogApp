package acitivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import universite.com.parasite.Constants;
import universite.com.parasite.R;

public class PerPostActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mPostTitle, mPostBody;
    private LinearLayout linearLayout;
    private Button commentsBtn;
    private  String post_id;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_post);

        toolbar = findViewById(R.id.per_post_toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));

        linearLayout = findViewById(R.id.per_post_layout);

        mPostTitle = findViewById(R.id.per_post_title);
        mPostBody = findViewById(R.id.per_post_body);

        commentsBtn = findViewById(R.id.per_post_comment_btn);

        post_id = getIntent().getStringExtra("POST_ID");
        String title = getIntent().getStringExtra("TITLE");
        String body = getIntent().getStringExtra("BODY");
        String[] images = getIntent().getStringArrayExtra("IMAGES");

        mPostTitle.setText(capitalize(title));
        mPostBody.setText(Html.fromHtml(body));

        final boolean[] isImageFitToScreen = {false};

        if(images!=null)
            for (final String image : images){
                Log.v(PerPostActivity.class.getSimpleName(), image+"\n");
                final ImageView imageView = new ImageView(this);
                imageView.setMaxWidth(linearLayout.getWidth());
                imageView.setMaxHeight(imageView.getHeight());
                imageView.setPadding(20, 0, 20, 0);
//                Glide.with(this).load(Constants.HOST_URL + "/images/postimg/"+image).into(imageView);
                Picasso.get().load(Constants.HOST_URL + "/images/postimg/"+image).into(imageView);
                linearLayout.addView(imageView, 3);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }

        commentsBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.per_post_comment_btn : {
                Intent intent = new Intent(PerPostActivity.this, CommentsActivity.class);
                intent.putExtra("post_id", post_id);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            }
        }
    }

    private String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
//        super.onBackPressed();
    }
}
