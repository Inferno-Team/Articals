package com.inferno.mobile.articals.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.databinding.ArticleItemBinding;
import com.inferno.mobile.articals.models.MasterRequest;
import com.inferno.mobile.articals.utils.RequestStatus;

import java.util.ArrayList;
import java.util.Calendar;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestHolder> {
    private final Context context;
    private final ArrayList<MasterRequest> requests;
    private AdapterOnClickListener onClickListener, onApprovedRequestClickListener;

    public void setOnClickListener(AdapterOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public RequestAdapter(Context context, ArrayList<MasterRequest> requests) {
        this.context = context;
        this.requests = requests;
    }

    @NonNull
    @Override
    public RequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RequestHolder(ArticleItemBinding.inflate(LayoutInflater.from(context),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RequestHolder holder, int position) {
        MasterRequest request = requests.get(position);
        holder.binding.setRequest(request);
        holder.binding.downloadNumber.setVisibility(View.GONE);
        holder.binding.fieldName.setVisibility(View.GONE);
        holder.binding.comments.setVisibility(View.GONE);
        switch (request.getStatus()) {
            case banned: {
                holder.binding.card.setBackgroundColor(
                        context.getResources().getColor(R.color.banned_article_background)
                );
                break;
            }
            case approved: {
                holder.binding.card.setBackgroundColor(
                        context.getResources().getColor(R.color.approved_article_background)
                );
                break;
            }
            default:
                holder.binding.card.setBackgroundColor(
                        context.getResources().getColor(R.color.article_background)
                );
                break;
        }
        holder.binding.getRoot().setOnClickListener(v -> {
            if (request.getStatus() == RequestStatus.approved) {
                if (onApprovedRequestClickListener != null)
                    onApprovedRequestClickListener.onClick(request.getId(), holder.getAdapterPosition());
            } else if (onClickListener != null)
                onClickListener.onClick(request.getId(), holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public void setOnApprovedRequestClickListener(AdapterOnClickListener onApprovedRequestClickListener) {
        this.onApprovedRequestClickListener = onApprovedRequestClickListener;
    }

    public static class RequestHolder extends RecyclerView.ViewHolder {
        ArticleItemBinding binding;

        public RequestHolder(@NonNull ArticleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
