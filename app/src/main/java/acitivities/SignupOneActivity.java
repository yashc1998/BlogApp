package acitivities;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import universite.com.parasite.R;

public class SignupOneActivity extends AppCompatActivity {

    private Button signupLogin_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_one);

        signupLogin_Btn = findViewById(R.id.signupOne_login_btn);

        signupLogin_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SignupOneActivity.this, signupLogin_Btn, "login-btn" );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(new Intent(SignupOneActivity.this, LoginActivity.class), optionsCompat.toBundle());
                }else{
                    startActivity(new Intent(SignupOneActivity.this, LoginActivity.class));
                }
            }
        });
    }


}
