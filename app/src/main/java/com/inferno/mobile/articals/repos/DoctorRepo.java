package com.inferno.mobile.articals.repos;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.inferno.mobile.articals.App;
import com.inferno.mobile.articals.models.ApprovedArticle;
import com.inferno.mobile.articals.models.MasterRequest;
import com.inferno.mobile.articals.models.MessageResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorRepo {
    private static DoctorRepo instance;
    private static final String TAG = "DoctorRepo";

    public static DoctorRepo getInstance() {
        if (instance == null)
            instance = new DoctorRepo();

        return instance;
    }

    private DoctorRepo() {
    }


    public MutableLiveData<ArrayList<MasterRequest>> getMasterRequest(String token) {
        MutableLiveData<ArrayList<MasterRequest>> liveData = new MutableLiveData<>();

        App.getAPI().getAllMasterRequests("Bearer " + token)
                .enqueue(new Callback<ArrayList<MasterRequest>>() {
                    @Override
                    public void onResponse(Call<ArrayList<MasterRequest>> call,
                                           Response<ArrayList<MasterRequest>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            liveData.postValue(response.body());
                        } else Log.e(TAG, "getMasterRequest@onResponse #" + response.code());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<MasterRequest>> call, Throwable t) {
                        Log.e(TAG, "getMasterRequest@onFailure", t);
                    }
                });

        return liveData;
    }

    public MutableLiveData<MessageResponse<ApprovedArticle>> approveArticle(String token, int id) {
        MutableLiveData<MessageResponse<ApprovedArticle>> liveData = new MutableLiveData<>();
        App.getAPI().approveArticle("Bearer " + token, id)
                .enqueue(new Callback<MessageResponse<ApprovedArticle>>() {
                    @Override
                    public void onResponse(Call<MessageResponse<ApprovedArticle>> call,
                                           Response<MessageResponse<ApprovedArticle>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            liveData.postValue(response.body());
                        } else Log.e(TAG, "approveArticle@onResponse #" + response.code());
                    }

                    @Override
                    public void onFailure(Call<MessageResponse<ApprovedArticle>> call, Throwable t) {
                        Log.e(TAG, "approveArticle@onFailure", t);
                    }
                });

        return liveData;
    }

}
