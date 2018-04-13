package fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import universite.com.parasite.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdmissionsFragment extends Fragment {

    private CollapsingToolbarLayout collapsingLayout;
    private ImageView admImage;


    public AdmissionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admissions, container, false);

        collapsingLayout = view.findViewById(R.id.collapsingToolbar);
        admImage = view.findViewById(R.id.toolbarImage);

        Glide.with(getContext()).load(R.drawable.nature).into(admImage);
//        Picasso.get().load(R.drawable.nature).placeholder(R.drawable.bg).into(admImage);

        collapsingLayout.setCollapsedTitleTextColor(Color.WHITE);

        return view;
    }

}
