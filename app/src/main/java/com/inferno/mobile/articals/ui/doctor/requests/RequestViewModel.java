package com.inferno.mobile.articals.ui.doctor.requests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.articals.models.ApprovalType;
import com.inferno.mobile.articals.models.ApprovedArticle;
import com.inferno.mobile.articals.models.MasterRequest;
import com.inferno.mobile.articals.models.MessageResponse;
import com.inferno.mobile.articals.models.User;
import com.inferno.mobile.articals.repos.AdminRepo;
import com.inferno.mobile.articals.repos.DoctorRepo;

import java.util.ArrayList;

public class RequestViewModel extends ViewModel {
    private DoctorRepo repo;
    private AdminRepo adminRepo;

    void init() {
        repo = DoctorRepo.getInstance();
        adminRepo = AdminRepo.getInstance();
    }

    LiveData<ArrayList<MasterRequest>> getMasterRequests(String token) {
        return repo.getMasterRequest(token);
    }

    LiveData<MessageResponse<ApprovedArticle>> approveArticle(String token, int articleId) {
        return repo.approveArticle(token, articleId);
    }

    public LiveData<ArrayList<User>> getAdminRequest(String token) {
        return adminRepo.getUsersRequests(token);
    }

    public LiveData<MessageResponse<Object>> approveUser(String token, ApprovalType type, int id) {
        return adminRepo.approveUser(token,type.name(),id);
    }
}
