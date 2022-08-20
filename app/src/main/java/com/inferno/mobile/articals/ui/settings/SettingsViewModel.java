package com.inferno.mobile.articals.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.articals.models.BannedUser;
import com.inferno.mobile.articals.models.MessageResponse;
import com.inferno.mobile.articals.repos.AdminRepo;
import com.inferno.mobile.articals.repos.HomeRepo;

import java.util.ArrayList;

public class SettingsViewModel extends ViewModel {
    private final String TAG = "SettingsViewModel";
    private AdminRepo adminRepo;
    void init(){
        adminRepo = AdminRepo.getInstance();
    }
    public LiveData<ArrayList<BannedUser>> getBannedUsers(String token) {
        return adminRepo.getBannedUsers(token);
    }

    public LiveData<MessageResponse<String>> unBanUser(String token, int id) {
        return adminRepo.unBanUser(token, id);
    }
}
