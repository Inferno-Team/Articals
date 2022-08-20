package com.inferno.mobile.articals.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.databinding.CommentItemBinding;
import com.inferno.mobile.articals.models.Comment;

import java.util.ArrayList;

public class CommentRVAdapter extends RecyclerView.Adapter<CommentRVAdapter.CommentHolder> {
    private final Context context;
    private final ArrayList<Comment> comments;
    private AdapterOnClickListener onCommentClickListener;

    public CommentRVAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(context).
                inflate(R.layout.comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        Comment comment = comments.get(position);
        comment.getUser().setFullName(comment.getUser().getFirstName()
                + " " + comment.getUser().getLastName());
        holder.binding.setComment(comments.get(position));
        holder.itemView.setOnClickListener(v -> {
            if (onCommentClickListener != null) {
                int index = holder.getAdapterPosition();
                onCommentClickListener.onClick(comments.get(index).getId(), index);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setOnCommentClickListener(AdapterOnClickListener onCommentClickListener) {
        this.onCommentClickListener = onCommentClickListener;
    }

    public static class CommentHolder extends RecyclerView.ViewHolder {
        CommentItemBinding binding;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            binding = CommentItemBinding.bind(itemView);
        }
    }
}
