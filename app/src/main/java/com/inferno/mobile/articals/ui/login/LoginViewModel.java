package com.inferno.mobile.articals.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.articals.models.LoginResponse;
import com.inferno.mobile.articals.repos.LoginRepo;

public class LoginViewModel extends ViewModel {
    private LoginRepo loginRepo;

    void init() {
        loginRepo = LoginRepo.getInstance();
    }

    public LiveData<LoginResponse> login(String email, String password) {
        return loginRepo.login(email, password);
    }
}
