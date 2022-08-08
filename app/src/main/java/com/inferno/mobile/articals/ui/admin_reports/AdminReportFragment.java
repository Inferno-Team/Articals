package com.inferno.mobile.articals.ui.admin_reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.inferno.mobile.articals.adapters.ReportAdapter;
import com.inferno.mobile.articals.databinding.AdminReportFragmnetBinding;
import com.inferno.mobile.articals.models.Report;
import com.inferno.mobile.articals.utils.Token;

import java.util.ArrayList;

public class AdminReportFragment extends Fragment {
    private AdminReportFragmnetBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = AdminReportFragmnetBinding.inflate(inflater, container, false);
        ReportViewModel viewModel = new ViewModelProvider(requireActivity())
                .get(ReportViewModel.class);
        viewModel.getAllReports(Token.getToken(requireContext()))
                .observe(requireActivity(), this::onReportArrive);
        return binding.getRoot();
    }

    private void onReportArrive(ArrayList<Report> reports) {
        ReportAdapter adapter = new ReportAdapter(reports);
        binding.reportRv.setAdapter(adapter);
    }
}
