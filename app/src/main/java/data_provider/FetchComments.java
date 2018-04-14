package data_provider;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controllers.CommentsAsyncResponce;
import modals.CommentsModal;
import universite.com.parasite.VolleySingleton;

import static universite.com.parasite.Constants.BASE_URL;

public class FetchComments {
    private Context ctx;

    public FetchComments(Context ctx){
        this.ctx = ctx;
    }

    public void fetchComments(final String p_id, final CommentsAsyncResponce callback){

        final ArrayList<CommentsModal> comments = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.POST, BASE_URL + "fetch_comments.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray mainArray = new JSONArray(response);
                    Log.v(FetchComments.class.getSimpleName(), response.toString());
                    for(int i=0; i<mainArray.length(); i++) {

                        JSONObject object = mainArray.getJSONObject(i);

                        String commentId = object.getString("c_id");
                        String commentBody = object.getString("c_body");
                        String uName = object.getString("u_name");
                        String uPic = object.getString("u_pic");


                        comments.add(new CommentsModal(uName, commentId, commentBody, "6", uPic));

                    }
                    callback.CommentfetchSuccess(comments);

                } catch (JSONException e) {
                    e.printStackTrace();
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
                map.put("p_id", p_id);
                return map;
            }
        };

        VolleySingleton.getInstance(ctx).addToRequestQueue(request);
    }
}
