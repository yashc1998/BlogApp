package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import modals.CommentsModal;
import view_holders.CommentsHolder;
import universite.com.parasite.R;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsHolder> {

    private ArrayList<CommentsModal> commentsModals = new ArrayList<>();
    private Context ctx;

    public CommentsAdapter(ArrayList<CommentsModal> commentsModals, Context ctx) {
        this.commentsModals = commentsModals;
        this.ctx = ctx;
    }

    @Override
    public CommentsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentsHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentsHolder holder, int position) {

        holder.updateCommentUI(commentsModals.get(position));

    }

    @Override
    public int getItemCount() {
        return commentsModals.size();
    }
}
