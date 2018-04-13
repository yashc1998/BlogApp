package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

import modals.PostModal;
import view_holders.InNewsHolder;
import universite.com.parasite.R;

/**
 * Created by yashc on 22-03-2018.
 */

public class InNewsAdapter extends RecyclerView.Adapter<InNewsHolder> implements Filterable{

    private static String TAG = InNewsAdapter.class.getSimpleName();

    ArrayList<PostModal> posts = new ArrayList<>();
    ArrayList<PostModal> mFilteredList = new ArrayList<>();
    Context ctx;

    public InNewsAdapter(ArrayList<PostModal> posts, Context ctx) {
        this.posts = posts;
        this.mFilteredList = posts;
        this.ctx = ctx;
    }

    @Override
    public InNewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_item, parent, false);
        return new InNewsHolder(view);
    }

    @Override
    public void onBindViewHolder(InNewsHolder holder, int position) {


            holder.updateInNewsUI(mFilteredList.get(position), ctx);
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();


                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    filterResults.count = posts.size();
                    filterResults.values = posts;
                } else {

                    ArrayList<PostModal> filteredList = new ArrayList<>();

                    for (PostModal post : posts) {

                        if (post.getmPostTitle().toLowerCase().contains(charString) || post.getmCreatorContentText().toLowerCase().contains(charString)) {
                            filteredList.add(post);
                        }
                    }

                    filterResults.count = filteredList.size();
                    filterResults.values = filteredList;
                }

                return filterResults;

            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<PostModal>) filterResults.values;
                notifyDataSetChanged();

            }


        };
    }
}
