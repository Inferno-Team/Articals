package com.inferno.mobile.articals.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.databinding.ReportItemBinding;
import com.inferno.mobile.articals.models.BannedUser;
import com.inferno.mobile.articals.models.User;

import java.util.ArrayList;
import java.util.Calendar;

public class BannedUserAdapter extends RecyclerView.Adapter<BannedUserAdapter.ViewHolder> {
    private final ArrayList<BannedUser> users;
    private AdapterOnClickListener onBannedUserClickListener;

    public BannedUserAdapter(ArrayList<BannedUser> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BannedUser user = users.get(position);
        String fullName = user.getUser().getFirstName() + " " + user.getUser().getLastName();
        holder.binding.reporterName.setText(fullName);
        holder.binding.cuase.setText(user.getCause());
        holder.binding.reportDate.setText(user.getCreatedAt());
        holder.itemView.setOnClickListener(v -> {
            if (onBannedUserClickListener != null) {
                onBannedUserClickListener.onClick(user.getId(),
                        holder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setOnBannedUserClickListener(AdapterOnClickListener onBannedUserClickListener) {
        this.onBannedUserClickListener = onBannedUserClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ReportItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ReportItemBinding.bind(itemView);
        }
    }
}
