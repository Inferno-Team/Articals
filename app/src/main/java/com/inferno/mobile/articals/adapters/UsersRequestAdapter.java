package com.inferno.mobile.articals.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.databinding.UserRequestItemBinding;
import com.inferno.mobile.articals.models.User;

import java.util.ArrayList;

public class UsersRequestAdapter extends
        RecyclerView.Adapter<UsersRequestAdapter.RequestViewHolder> {
    private final ArrayList<User> users;
    private AdapterOnClickListener onClickListener;

    public UsersRequestAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RequestViewHolder(LayoutInflater.
                from(parent.getContext()).inflate
                        (R.layout.user_request_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        holder.binding.setUser(users.get(position));
        holder.itemView.setOnClickListener(v -> {
            if (onClickListener != null) {
                int pos = holder.getAdapterPosition();
                onClickListener.onClick(users.get(pos).getId(), pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setOnClickListener(AdapterOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        UserRequestItemBinding binding;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = UserRequestItemBinding.bind(itemView);
        }
    }
}
