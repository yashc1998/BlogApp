package controllers;

import java.util.ArrayList;

import modals.PostModal;

public interface PostsAsyncResponce {
    void fetchSuccess(ArrayList<PostModal> posts);
}
