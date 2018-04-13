package acitivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import modals.UserModal;
import universite.com.parasite.Constants;
import universite.com.parasite.R;
import universite.com.parasite.SharedPrefManager;
import universite.com.parasite.VolleySingleton;

import static universite.com.parasite.Constants.BASE_URL;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button mLoginSignup_btn, login_btn;
    private ProgressDialog loginLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if(SharedPrefManager.getInstance(LoginActivity.this).isLoggedIn()){
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        mLoginSignup_btn = findViewById(R.id.login_signup_btn);
        login_btn = findViewById(R.id.login_btn);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);

        mLoginSignup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupOneActivity.class));
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                if(validateFields(email, password)) {
                    UserLogin(email, password);
                }
            }
        });
    }

    private void UserLogin(final String email, final String password){

        loginLoading = new ProgressDialog(this);
        loginLoading.setMessage("Please wait...");
        loginLoading.show();

        StringRequest loginRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL + Constants.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject main = new JSONObject(response);

                            Log.v("donkey", main.toString());

                            if(main.getString("error").equalsIgnoreCase("false")){
                                SharedPrefManager.getInstance(LoginActivity.this).userLogin(new UserModal(
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
                                ));

                                LoginActivity.this.finish();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                loginLoading.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loginLoading.dismiss();
                        Toast.makeText(LoginActivity.this, "Unable to Login. Please try again later : "+error.toString(), Toast.LENGTH_SHORT).show();
                }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> loginMap = new HashMap<>();
                loginMap.put("email", email);
                loginMap.put("password", password);

                return loginMap;
            }
        };

        VolleySingleton.getInstance(LoginActivity.this).addToRequestQueue(loginRequest);
    }


    private boolean validateFields(String email, String password){
        if(TextUtils.isEmpty(email)){
            loginEmail.setError("Field Empty");
            return false;
        }else if(TextUtils.isEmpty(password)){
            loginPassword.setError("Field Empty");
            return false;
        }

        return true;
    }
}
