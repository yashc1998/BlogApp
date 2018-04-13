package data_provider;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import acitivities.LoginActivity;
import acitivities.MainActivity;
import controllers.UserAsyncResponce;
import modals.PostModal;
import modals.UserModal;
import universite.com.parasite.Constants;
import universite.com.parasite.SharedPrefManager;
import universite.com.parasite.VolleySingleton;

import static universite.com.parasite.Constants.BASE_URL;

public class UserData {

    private Context ctx;

    public UserData(Context ctx) {
        this.ctx = ctx;
    }

    public void getUserData(final String id, final UserAsyncResponce userAsyncResponce){
        StringRequest loginRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL + "fetch_user_by_id.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            UserModal modal = null;
                            JSONObject main = new JSONObject(response);

                            Log.v("donkey", main.toString());

                            if(main.getString("error").equalsIgnoreCase("false")){
                                modal = new UserModal(
                                        main.getString("id"),
                                        main.getString("fullname"),
                                        main.getString("username"),
                                        main.getString("email"),
                                        main.getString("token_id"),
                                        main.getString("college"),
                                        main.getString("course"),
                                        main.getString("year"),
                                        main.getString("sem"),
                                        main.getString("reg_date"),
                                        main.getString("profile_pic")
                                );
                                SharedPrefManager.getInstance(ctx).userLogin(modal);
                            }
                            userAsyncResponce.userdata(modal);
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
                Map<String, String> loginMap = new HashMap<>();
                loginMap.put("id", id);

                return loginMap;
            }
        };

        VolleySingleton.getInstance(ctx).addToRequestQueue(loginRequest);
    }
}
