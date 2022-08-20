package com.inferno.mobile.articals.ui.admin_reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.inferno.mobile.articals.adapters.ReportAdapter;
import com.inferno.mobile.articals.databinding.AdminReportFragmnetBinding;
import com.inferno.mobile.articals.models.ApprovalType;
import com.inferno.mobile.articals.models.Report;
import com.inferno.mobile.articals.utils.Token;

import java.util.ArrayList;

public class AdminReportFragment extends Fragment {
    private AdminReportFragmnetBinding binding;
    private ReportViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = AdminReportFragmnetBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity())
                .get(ReportViewModel.class);
        viewModel.getAllReports(Token.getToken(requireContext()))
                .observe(requireActivity(), this::onReportArrive);
        return binding.getRoot();
    }

    private void onReportArrive(ArrayList<Report> reports) {
        ReportAdapter adapter = new ReportAdapter(reports);
        adapter.setOnReportClickListener((id, pos) -> {
            // ban user
            /* ;*/


            new AlertDialog.Builder(requireContext())
                    .setTitle("Ban User")
                    .setMessage("Do you want to ban this user ?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Report report = reports.get(pos);
                        viewModel.banUser(Token.getToken(requireContext()), id,
                                report.getCause()).observe(requireActivity(),
                                messageResponse -> {
                                    Toast.makeText(requireContext(),
                                            messageResponse.getMessage(), Toast.LENGTH_LONG).show();
                                    if (messageResponse.getCode() == 200) {
                                        // search in all reports and remove all reports with this user id
                                        ArrayList<Report> posArray = new ArrayList<>();
                                        for (int i = 0; i < reports.size(); i++) {
                                            if (reports.get(i).getComment().getUser().getId() == id) {
                                                posArray.add(reports.get(i));
                                            }
                                        }
                                        reports.removeAll(posArray);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                        dialog.dismiss();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        });
        binding.reportRv.setAdapter(adapter);
    }
}
