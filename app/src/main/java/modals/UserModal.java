package modals;

import android.support.annotation.Nullable;

/**
 * Created by yashc on 24-03-2018.
 */

public class UserModal {

    private String mID;
    private String mFullName, mUsername, mEmail, mCollege, mCourse;
    private String mYear, mSem;
    private String mRegisterDate;
    private String mProfilePic;
    private String tokenID;

    public UserModal(String mID, String mFullName, String mUsername, String mEmail, @Nullable String tokenID, String mCollege, String mCourse, String mYear, String mSem, String mRegisterDate, String mProfilePic) {
        this.mID = mID;
        this.mFullName = mFullName;
        this.mUsername = mUsername;
        this.mEmail = mEmail;
        this.mCollege = mCollege;
        this.mCourse = mCourse;
        this.mYear = mYear;
        this.mSem = mSem;
        this.mRegisterDate = mRegisterDate;
        this.mProfilePic = mProfilePic;
        this.tokenID = tokenID;
    }

    public String getmFullName() {
        return mFullName;
    }

    public String getmUsername() {
        return mUsername;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmCollege() {
        return mCollege;
    }

    public String getmCourse() {
        return mCourse;
    }

    public String getmYear() {
        return mYear;
    }

    public String getmSem() {
        return mSem;
    }

    public String getmRegisterDate() {
        return mRegisterDate;
    }

    public String getmProfilePic() {
        return mProfilePic;
    }

    public String getmID() {
        return mID;
    }

    public String getTokenID() {
        return tokenID;
    }
}
