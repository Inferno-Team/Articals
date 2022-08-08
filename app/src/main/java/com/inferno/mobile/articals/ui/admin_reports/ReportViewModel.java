package com.inferno.mobile.articals.ui.admin_reports;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.articals.models.Report;
import com.inferno.mobile.articals.repos.AdminRepo;

import java.util.ArrayList;

public class ReportViewModel extends ViewModel {
    private final AdminRepo adminRepo;

    public ReportViewModel() {
        this.adminRepo = AdminRepo.getInstance();
    }

    public LiveData<ArrayList<Report>> getAllReports(String token) {
        return adminRepo.getAllReports(token);
    }
}
