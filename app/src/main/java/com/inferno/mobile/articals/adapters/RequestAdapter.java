package com.inferno.mobile.articals.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inferno.mobile.articals.databinding.ArticleItemBinding;
import com.inferno.mobile.articals.models.MasterRequest;

import java.util.ArrayList;
import java.util.Calendar;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestHolder> {
    private final Context context;
    private final ArrayList<MasterRequest> requests;
    private AdapterOnClickListener onClickListener;

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


        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        Calendar requestTime = Calendar.getInstance();
        requestTime.setTime(request.getCreateAt());
        StringBuilder time = new StringBuilder();
        Calendar diff = Calendar.getInstance();

        diff.setTimeInMillis(now.getTimeInMillis() - requestTime.getTimeInMillis());
        int years = diff.get(Calendar.YEAR);
        int months = diff.get(Calendar.MONTH);
        int days = diff.get(Calendar.DAY_OF_MONTH);

        int hours = diff.get(Calendar.HOUR);
        int min = diff.get(Calendar.MINUTE);
        System.out.println("years : " + years);
        System.out.println("months : " + months);
        System.out.println("days : " + days);
        System.out.println("hours : " + hours);
        System.out.println("min : " + min);
        if (years == 1970) {
            if (months == 0) {
                if (days == 1) {
                    time.append(hours).append(" hours ").append(min).append(" min");
                } else {
                    time.append(days).append(" days");
                }
            } else {
                time.append(months).append(" months");
            }
        } else {
            time.append(years).append(" years");
        }

        holder.binding.uploadTime.setText(time.toString());
        holder.binding.getRoot().setOnClickListener(v->{
            if(onClickListener!=null)
                onClickListener.onClick(request.getId(),holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public static class RequestHolder extends RecyclerView.ViewHolder {
        ArticleItemBinding binding;

        public RequestHolder(@NonNull ArticleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
