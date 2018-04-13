package fragments;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import universite.com.parasite.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private CollapsingToolbarLayout toolbarLayout;


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        toolbarLayout = view.findViewById(R.id.collapsingToolbar_about);
        toolbarLayout.setTitle("About Us");
        toolbarLayout.setTitleEnabled(true);
        toolbarLayout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.WHITE));

        return view;
    }

}
