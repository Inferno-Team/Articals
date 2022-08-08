package com.inferno.mobile.articals.repos;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inferno.mobile.articals.App;
import com.inferno.mobile.articals.models.MessageResponse;
import com.inferno.mobile.articals.models.Report;
import com.inferno.mobile.articals.models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRepo {
    private static AdminRepo instance;
    private final String TAG = AdminRepo.class.getName();

    public static AdminRepo getInstance() {
        if (instance == null)
            instance = new AdminRepo();
        return instance;
    }

    private AdminRepo() {

    }

    public MutableLiveData<ArrayList<User>> getUsersRequests(String token) {
        MutableLiveData<ArrayList<User>> liveData = new MutableLiveData<>();
        App.getAPI().getAllUsersRequests("Bearer " + token)
                .enqueue(new Callback<ArrayList<User>>() {
                    @Override
                    public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                        if (response.isSuccessful() && response.body() != null)
                            liveData.postValue(response.body());
                        else Log.e(TAG, "getUsersRequests@onResponse #" + response.code());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                        Log.e(TAG, "getUsersRequests@onFailure", t);
                    }
                });
        return liveData;
    }

    public MutableLiveData<MessageResponse<Object>> approveUser(String token,String type, int id) {
        MutableLiveData<MessageResponse<Object>> liveData = new MutableLiveData<>();
        App.getAPI().approveUser("Bearer "+token,type,id)
                .enqueue(new Callback<MessageResponse<Object>>() {
                    @Override
                    public void onResponse(Call<MessageResponse<Object>> call,
                                           Response<MessageResponse<Object>> response) {
                        if (response.isSuccessful() && response.body() != null)
                            liveData.postValue(response.body());
                        else Log.e(TAG, "approveUser@onResponse #" + response.code());
                    }

                    @Override
                    public void onFailure(Call<MessageResponse<Object>> call, Throwable t) {
                        Log.e(TAG, "approveUser@onFailure", t);
                    }
                });
        return liveData;
    }

    public MutableLiveData<ArrayList<Report>> getAllReports(String token) {
        MutableLiveData<ArrayList<Report>> liveData = new MutableLiveData<>();
        App.getAPI().getAllReport("Bearer "+token).enqueue(new Callback<ArrayList<Report>>() {
            @Override
            public void onResponse(Call<ArrayList<Report>> call,
                                   Response<ArrayList<Report>> response) {
                if (response.isSuccessful() && response.body() != null)
                    liveData.postValue(response.body());
                else Log.e(TAG, "getAllReports@onResponse #" + response.code());
            }

            @Override
            public void onFailure(Call<ArrayList<Report>> call, Throwable t) {
                Log.e(TAG, "getAllReports@onFailure", t);
            }
        });
        return liveData;
    }
}
