package com.inferno.mobile.articals.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.databinding.ReportItemBinding;
import com.inferno.mobile.articals.models.Report;

import java.util.ArrayList;
import java.util.Calendar;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private final ArrayList<Report> reports;

    public ReportAdapter(ArrayList<Report> reports) {
        this.reports = reports;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.report_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Report report = reports.get(position);
        holder.binding.reporterName.setText(report.getReporter().getFirstName());
        holder.binding.cuase.setText(report.getCause());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(report.getCreatedAt().getTime());
        String time = calendar.get(Calendar.YEAR)
                + "-" + (calendar.get(Calendar.MONTH) + 1)
                + "-" + (calendar.get(Calendar.DAY_OF_MONTH));
        holder.binding.reportDate.setText(time);

    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ReportItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ReportItemBinding.bind(itemView);
        }
    }
}
