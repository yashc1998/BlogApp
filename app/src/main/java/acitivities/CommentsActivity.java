package acitivities;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapters.CommentsAdapter;
import controllers.CommentsAsyncResponce;
import data_provider.FetchComments;
import modals.CommentsModal;
import universite.com.parasite.R;
import universite.com.parasite.SharedPrefManager;
import universite.com.parasite.VolleySingleton;

import static universite.com.parasite.Constants.BASE_URL;

public class CommentsActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView commentsRecyclerView;
    private ArrayList<CommentsModal> commentsModalArrayList = new ArrayList<>();
    private Toolbar toolbar;
    private ImageButton sendCommentBtn;
    private EditText comment;
    private String post_id;
    private ProgressDialog progressDialog;
    private TextView noComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        toolbar = findViewById(R.id.comments_toolbar);
        setSupportActionBar(toolbar);

        comment = findViewById(R.id.comment_edittext);

        sendCommentBtn = findViewById(R.id.send_comment_btn);
        sendCommentBtn.setOnClickListener(this);

        noComments = findViewById(R.id.no_comment);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(5f);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        commentsRecyclerView = findViewById(R.id.comments_recycler_view);
        commentsRecyclerView.setHasFixedSize(true);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        post_id = getIntent().getStringExtra("post_id");

        new FetchComments(this).fetchComments(post_id, new CommentsAsyncResponce() {
            @Override
            public void CommentfetchSuccess(ArrayList<CommentsModal> arrayList) {

                progressDialog.dismiss();

                if(arrayList.isEmpty()){
                    noComments.setVisibility(View.VISIBLE);
                }else {
                    CommentsAdapter commentsAdapter = new CommentsAdapter(arrayList, CommentsActivity.this);
                    commentsRecyclerView.setAdapter(commentsAdapter);
                    commentsRecyclerView.scrollToPosition(arrayList.size() - 1);
                }
            }
        });


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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.send_comment_btn:{
                if(!TextUtils.isEmpty(comment.getText().toString().trim())) {
                    String commentBody = comment.getText().toString().trim();
                    sendCommentToServer(commentBody);
                }
            }
        }
    }

    private void sendCommentToServer(final String commentBody) {

        StringRequest commentRequest = new StringRequest(Request.Method.POST, BASE_URL + "add_comment.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);

                    int error = object.getInt("error");
                    String msg = object.getString("msg");

                    Toast.makeText(CommentsActivity.this, msg, Toast.LENGTH_SHORT).show();
                    progressDialog.show();

                    new FetchComments(CommentsActivity.this).fetchComments(post_id, new CommentsAsyncResponce() {

                        @Override
                        public void CommentfetchSuccess(ArrayList<CommentsModal> arrayList) {

                            progressDialog.dismiss();

                            if(arrayList.isEmpty()){
                                noComments.setVisibility(View.VISIBLE);
                            }else {
                                CommentsAdapter commentsAdapter = new CommentsAdapter(arrayList, CommentsActivity.this);
                                commentsRecyclerView.setAdapter(commentsAdapter);
                                commentsRecyclerView.scrollToPosition(arrayList.size() - 1);
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CommentsActivity.this, "Could not add comment", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("comment", commentBody);
                map.put("u_id", SharedPrefManager.getInstance(CommentsActivity.this).getUser().getmID());
                map.put("p_id", post_id);
                return map;
            }
        };

        VolleySingleton.getInstance(CommentsActivity.this).addToRequestQueue(commentRequest);
    }
}
