package data_provider;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import controllers.PostsAsyncResponce;
import modals.PostModal;
import universite.com.parasite.Constants;
import universite.com.parasite.VolleySingleton;

import static universite.com.parasite.Constants.BASE_URL;

public class InNewsPosts{

    private Context ctx;

    public InNewsPosts(Context ctx){
        this.ctx = ctx;
    }

    public void fetchInNewsPosts(final PostsAsyncResponce callback){

        final ArrayList<PostModal> posts = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, BASE_URL + "fetch_posts.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray mainArray = new JSONArray(response);
                    Log.v(InNewsPosts.class.getSimpleName(), mainArray.toString());
                    for(int i=0; i<mainArray.length(); i++) {

                        JSONObject mainResponce = mainArray.getJSONObject(i);

                        String postID = mainResponce.getString("p_id");
                        String postTitle = mainResponce.getString("title");
                        String postBody = mainResponce.getString("body");
                        String images = mainResponce.getString("images");
                        String userID = mainResponce.getString("u_id");
                        String userName = mainResponce.getString("u_name");
                        String userProfileImage = mainResponce.getString("u_image");
                        String date = mainResponce.getString("date");
                        String categoryID = mainResponce.getString("cat_id");
                        String categoryName = mainResponce.getString("cat_name");

                        String postImages[] = new String[110];

                        if(!images.equalsIgnoreCase("")){
                            postImages = images.split("\\$");
                        }else{
                            postImages = null;
                        }

                        Log.v(InNewsPosts.class.getSimpleName(), Arrays.toString(postImages));

                        posts.add(new PostModal(postID, Constants.HOST_URL+"/images/"+userProfileImage, userName, date, postTitle, postBody, "44", postImages));

                    }
                    callback.fetchSuccess(posts);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleySingleton.getInstance(ctx).addToRequestQueue(request);
    }
}
