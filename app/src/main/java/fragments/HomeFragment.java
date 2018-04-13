package fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import acitivities.AddEventActivity;
import adapters.InNewsAdapter;
import controllers.PostsAsyncResponce;
import data_provider.InNewsPosts;
import modals.PostModal;
import universite.com.parasite.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public RecyclerView InNewsRecyclerView;
    ArrayList<PostModal> postModals = new ArrayList<>();
    public InNewsAdapter newsAdapter;
    private FloatingActionButton mAddPost;
    private SwipeRefreshLayout refreshPosts;
    private ShimmerFrameLayout shimmerFrameLayout;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        refreshPosts = view.findViewById(R.id.post_refresh_layout);
        shimmerFrameLayout = view.findViewById(R.id.post_shimmer_view_container);

        shimmerFrameLayout.startShimmerAnimation();

        mAddPost = view.findViewById(R.id.add_post_btn);
        mAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddEventActivity.class));
                ((Activity)view.getContext()).overridePendingTransition(R.anim.slide_up, R.anim.fade_scale_out);
            }
        });

        InNewsRecyclerView = view.findViewById(R.id.inNews_recyclerView);
        InNewsRecyclerView.setHasFixedSize(true);
        InNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        new InNewsPosts(getContext()).fetchInNewsPosts(new PostsAsyncResponce() {
            @Override
            public void fetchSuccess(ArrayList<PostModal> posts) {

                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                newsAdapter = new InNewsAdapter(posts, getActivity());
                InNewsRecyclerView.setAdapter(newsAdapter);
            }
        });

        refreshPosts.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shimmerFrameLayout.startShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                InNewsRecyclerView.setVisibility(View.INVISIBLE);
                new InNewsPosts(getContext()).fetchInNewsPosts(new PostsAsyncResponce() {
                    @Override
                    public void fetchSuccess(ArrayList<PostModal> posts) {

                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        InNewsRecyclerView.setVisibility(View.VISIBLE);
                        newsAdapter = new InNewsAdapter(posts, getActivity());
                        InNewsRecyclerView.setAdapter(newsAdapter);
                        if(refreshPosts.isRefreshing())
                            refreshPosts.setRefreshing(false);
                    }
                });
            }
        });


        return view;
    }

    public void search(String query){
        newsAdapter.getFilter().filter(query);
    }

}
