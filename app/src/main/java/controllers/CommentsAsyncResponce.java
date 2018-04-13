package controllers;

import java.util.ArrayList;

import modals.CommentsModal;

public interface CommentsAsyncResponce {

    void CommentfetchSuccess(ArrayList<CommentsModal> arrayList);
}
