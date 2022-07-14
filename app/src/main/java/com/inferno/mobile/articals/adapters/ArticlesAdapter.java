package com.inferno.mobile.articals.adapters;

import android.content.Context;
import android.view.LayoutInflater;
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

import java.util.ArrayList;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleHolder> {
    private final Context context;
    private ArrayList<Article> articles;
    private final ArrayList<Article> allArticles;
    private AdapterOnClickListener onClickListener;
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

    public void removeOther(ArrayList<Article> articles) {
        this.articles = new ArrayList<>(allArticles);
        ArrayList<Article> indexes = new ArrayList<>();
        for (int i = 0; i < this.articles.size(); i++) {
            Article article = this.articles.get(i);
            if (articles.contains(article)) continue;
            indexes.add(article);
        }
        this.articles.removeAll(indexes);
        notifyDataSetChanged();
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
        CommentRVAdapter adapter = new CommentRVAdapter(context, art.getComments());
        setCommentAdapter(holder, adapter);
        setCommentTitle(holder, art);

//        holder.binding.uploadTime.setText(time.toString());

        holder.binding.getRoot().setOnClickListener(v -> {
            if (onClickListener != null)
                onClickListener.onClick(art.getId(), holder.getAdapterPosition());
        });

        if (!isRecent) {
            if (art.getApproved() == null)
                holder.binding.card.setCardBackgroundColor(
                        context.getResources().getColor(R.color.article_background));
            else if (art.getApproved() != null)
                holder.binding.card.setCardBackgroundColor(
                        context.getResources().getColor(R.color.approved_article_background));
            else holder.binding.card.setCardBackgroundColor(
                        context.getResources().getColor(R.color.banned_article_background));
        }
    }

    private void setCommentAdapter(@NonNull ArticleHolder holder, CommentRVAdapter adapter) {
        ((RecyclerView) ((LinearLayout) ((MaterialCardView)
                holder.binding.comments.getChildAt(0)).getChildAt(0))
                .getChildAt(1))
                .setAdapter(adapter);
    }

    private void setCommentTitle(@NonNull ArticleHolder holder, Article article) {
        String title = "Comments (" + article.getComments().size() + ")";
        ((TextView) ((RelativeLayout) ((LinearLayout) ((MaterialCardView)
                holder.binding.comments.getChildAt(0)).getChildAt(0))
                .getChildAt(0)).getChildAt(1)).setText(title);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ArticleHolder extends RecyclerView.ViewHolder {

        ArticleItemBinding binding;

        public ArticleHolder(ArticleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
