package com.inferno.mobile.articals.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.databinding.ArticleItemBinding;
import com.inferno.mobile.articals.models.Article;
import com.inferno.mobile.articals.models.Comment;

import java.util.ArrayList;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleHolder> {
    private final Context context;
    private ArrayList<Article> articles;
    private final ArrayList<Article> allArticles;
    private AdapterOnClickListener onClickListener, onLongClickListener,
            onCommentClickListener, onAddCommentClickListener;
    private final boolean isRecent;

    public void setOnClickListener(AdapterOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ArticlesAdapter(Context context, ArrayList<Article> articles, boolean isRecent) {
        this.context = context;
        this.articles = articles;
        this.allArticles = new ArrayList<>(articles);
        this.isRecent = isRecent;
    }

    public void swapArticle(ArrayList<Article> articles) {
        this.articles.clear();
        this.articles.addAll(articles);
        notifyDataSetChanged();
    }

    public void returnToRecent() {
        this.articles.clear();
        this.articles.addAll(allArticles);
        notifyDataSetChanged();
    }

    public void removeOne(int pos) {
        this.articles.remove(pos);
        this.notifyItemRemoved(pos);
    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleHolder(ArticleItemBinding.inflate(LayoutInflater.from(context),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
        Article art = articles.get(position);
        holder.binding.setArticle(art);
        if (art.getComments() == null)
            art.setComments(new ArrayList<>());
        CommentRVAdapter adapter = new CommentRVAdapter(context, art.getComments());
        adapter.setOnCommentClickListener(onCommentClickListener);
        setCommentAdapter(holder, adapter);
        setCommentTitle(holder, art);
        holder.binding.getRoot().setOnClickListener(v -> {
            if (onClickListener != null)
                onClickListener.onClick(art.getId(), holder.getAdapterPosition());
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (onLongClickListener != null)
                onLongClickListener.onClick(art.getId(), holder.getAdapterPosition());
            return true;
        });

        if (!isRecent) {
            if (art.getApproved() == null && art.getBanned() == null)
                holder.binding.card.setCardBackgroundColor(
                        context.getResources().getColor(R.color.article_background));
            else if (art.getApproved() != null)
                holder.binding.card.setCardBackgroundColor(
                        context.getResources().getColor(R.color.approved_article_background));
            else {
                holder.binding.card.setCardBackgroundColor(
                        context.getResources().getColor(R.color.banned_article_background));
                holder.binding.comments.setVisibility(View.GONE);
                holder.binding.uploadTime.setText(
                        art.getBanned().getCreatedAt()
                );
            }
            holder.binding.comments.setVisibility(View.GONE);
            holder.binding.addComment.setVisibility(View.GONE);
        }
        holder.binding.addComment.setOnClickListener(v -> {
            if (onAddCommentClickListener != null)
                onAddCommentClickListener.onClick(art.getId(), holder.getAdapterPosition());
        });
    }

    private void setCommentAdapter(@NonNull ArticleHolder holder, CommentRVAdapter adapter) {
        ((RecyclerView) ((LinearLayout) ((MaterialCardView)
                holder.binding.comments.getChildAt(0)).getChildAt(0))
                .getChildAt(1))
                .swapAdapter(adapter,true);
    }

    private void setCommentTitle(@NonNull ArticleHolder holder, Article article) {
        int size = 0;
        if (article.getComments() != null)
            size = article.getComments().size();
        String title = "Comments [" + size + "]";
        ((TextView) ((RelativeLayout) ((LinearLayout) ((MaterialCardView)
                holder.binding.comments.getChildAt(0)).getChildAt(0))
                .getChildAt(0)).getChildAt(1)).setText(title);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnLongClickListener(AdapterOnClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public void setOnCommentClickListener(AdapterOnClickListener onCommentClickListener) {
        this.onCommentClickListener = onCommentClickListener;
    }

    public void setOnAddCommentClickListener(AdapterOnClickListener onAddCommentClickListener) {
        this.onAddCommentClickListener = onAddCommentClickListener;
    }

    public void addNewComment(int pos, Comment comment) {
        articles.get(pos).getComments().add(comment);
        notifyItemChanged(pos);
    }

    public static class ArticleHolder extends RecyclerView.ViewHolder {

        ArticleItemBinding binding;

        public ArticleHolder(ArticleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
