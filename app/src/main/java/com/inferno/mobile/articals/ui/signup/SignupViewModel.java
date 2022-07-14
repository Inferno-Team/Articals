package com.inferno.mobile.articals.ui.signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.articals.models.Field;
import com.inferno.mobile.articals.models.LoginResponse;
import com.inferno.mobile.articals.repos.LoginRepo;

import java.util.ArrayList;

public class SignupViewModel extends ViewModel {
    private LoginRepo loginRepo = LoginRepo.getInstance();

    public LiveData<ArrayList<Field>> getAllFields() {
        return loginRepo.getAllFields();
    }

    public LiveData<LoginResponse> signUp(String firstName, String lastName,
                                          String email, String password,
                                          String userType, int fieldId,
                                          String fcmToken) {
        return loginRepo.signup(firstName, lastName, email, password, userType, fieldId, fcmToken);
    }
}
