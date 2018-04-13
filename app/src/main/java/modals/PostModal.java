package modals;

public class PostModal {
    private String mCreatorProfImage, mCreatorName, mCreatorTime, mCreatorContentText, mTotalComments, mPostTitle;
    private String mCreatorContentImage[];
    private String mPostID;

    public PostModal(String mPostID, String mCreatorProfImage, String mCreatorName, String mCreatorTime, String title, String mCreatorContentText, String mTotalComments, String... mCreatorContentImage) {
        this.mPostID = mPostID;
        this.mCreatorProfImage = mCreatorProfImage;
        this.mCreatorContentImage = mCreatorContentImage;
        this.mCreatorName = mCreatorName;
        this.mCreatorTime = mCreatorTime;
        this.mCreatorContentText = mCreatorContentText;
        this.mTotalComments = mTotalComments;
        this.mPostTitle = title;
    }

    public String getmPostID() {
        return mPostID;
    }

    public String getmCreatorProfImage() {
        return mCreatorProfImage;
    }

    public String[] getmCreatorContentImage() {
        return mCreatorContentImage;
    }

    public String getmCreatorName() {
        return mCreatorName;
    }

    public String getmCreatorTime() {
        return mCreatorTime;
    }

    public String getmCreatorContentText() {
        return mCreatorContentText;
    }

    public String getmTotalComments() {
        return mTotalComments;
    }

    public String getmPostTitle() {
        return mPostTitle;
    }
}
