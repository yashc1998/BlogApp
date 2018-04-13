package universite.com.parasite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import acitivities.LoginActivity;
import modals.UserModal;

/**
 * Created by yashc on 24-03-2018.
 */

public class SharedPrefManager {
    //the constants
    private static final String SHARED_PREF_NAME = "universitesharedpreference";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_PROFILEPIC = "keyprofilepic";
    private static final String KEY_REGISTERDATE = "keyregisterdate";
    private static final String KEY_FULLNAME = "keyfullname";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_COLLEGE = "keycollege";
    private static final String KEY_COURSE = "keycourse";
    private static final String KEY_ID = "keyid";
    private static final String KEY_YEAR = "keyyear";
    private static final String KEY_SEM = "keysem";
    private static final String KEY_TOKEN_ID = "keytokenid";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(UserModal user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, user.getmID());
        editor.putString(KEY_USERNAME, user.getmUsername());
        editor.putString(KEY_PROFILEPIC, user.getmProfilePic());
        editor.putString(KEY_REGISTERDATE, user.getmRegisterDate());
        editor.putString(KEY_FULLNAME, user.getmFullName());
        editor.putString(KEY_COLLEGE, user.getmCollege());
        editor.putString(KEY_COURSE, user.getmCourse());
        editor.putString(KEY_YEAR, user.getmYear());
        editor.putString(KEY_SEM, user.getmSem());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public UserModal getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserModal(
                sharedPreferences.getString(KEY_ID, null),
                sharedPreferences.getString(KEY_FULLNAME, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_TOKEN_ID, null),
                sharedPreferences.getString(KEY_COLLEGE, null),
                sharedPreferences.getString(KEY_COURSE, null),
                sharedPreferences.getString(KEY_YEAR, null),
                sharedPreferences.getString(KEY_SEM, null),
                sharedPreferences.getString(KEY_REGISTERDATE, null),
                sharedPreferences.getString(KEY_PROFILEPIC, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

}
