package com.inferno.mobile.articals.repos;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.inferno.mobile.articals.App;
import com.inferno.mobile.articals.models.Field;
import com.inferno.mobile.articals.models.LoginResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepo {

    private static LoginRepo instance;
    private final static String TAG = LoginRepo.class.toString();

    public static LoginRepo getInstance() {
        if (instance == null)
            instance = new LoginRepo();

        return instance;
    }

    private LoginRepo() {
    }

    public MutableLiveData<LoginResponse> login(String email, String password) {
        MutableLiveData<LoginResponse> liveData = new MutableLiveData<>();


        App.getAPI().login(email, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call,
                                   Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    liveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, "login#onFailure", t);
            }
        });
        return liveData;
    }

    public MutableLiveData<ArrayList<Field>> getAllFields() {
        MutableLiveData<ArrayList<Field>> liveData = new MutableLiveData<>();
        App.getAPI().getAllFields().enqueue(new Callback<ArrayList<Field>>() {
            @Override
            public void onResponse(Call<ArrayList<Field>> call,
                                   Response<ArrayList<Field>> response) {
                if (response.isSuccessful() && response.body() != null)
                    liveData.postValue(response.body());
                else Log.e(TAG, "getAllFields$onResponse code : #" + response.code());
            }

            @Override
            public void onFailure(Call<ArrayList<Field>> call, Throwable t) {
                Log.e(TAG, "getAllFields$onFailure", t);

            }
        });
        return liveData;
    }

    public MutableLiveData<LoginResponse> signup(String firstName, String lastName,
                                                 String email, String password,
                                                 String userType, int fieldId,
                                                 String fcmToken) {
        MutableLiveData<LoginResponse> liveData = new MutableLiveData<>();
        App.getAPI().signup(firstName, lastName, email, password, userType, fieldId, fcmToken)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call,
                                           Response<LoginResponse> response) {
                        if (response.isSuccessful() && response.body() != null)
                            liveData.postValue(response.body());
                        else Log.e(TAG, "signup$onResponse code : #" + response.code());
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.e(TAG, "signup$onFailure", t);
                    }
                });
        return liveData;
    }
}
