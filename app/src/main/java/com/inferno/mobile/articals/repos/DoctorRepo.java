package com.inferno.mobile.articals.repos;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inferno.mobile.articals.App;
import com.inferno.mobile.articals.models.ApprovedArticle;
import com.inferno.mobile.articals.models.Article;
import com.inferno.mobile.articals.models.BannedArticle;
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

    public MutableLiveData<MessageResponse<ApprovedArticle>>
    approveArticle(String token, int id) {
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

    public MutableLiveData<MessageResponse<Article>> removeArticle(String token, int id) {
        MutableLiveData<MessageResponse<Article>> liveData = new MutableLiveData<>();
        App.getAPI().removeDoctorArticle("Bearer "+token, id)
                .enqueue(new Callback<MessageResponse<Article>>() {
                    @Override
                    public void onResponse(Call<MessageResponse<Article>> call,
                                           Response<MessageResponse<Article>> response) {
                        if (response.isSuccessful() && response.body() != null)
                            liveData.postValue(response.body());
                        else Log.e(TAG, "removeArticle@onResponse #" + response.code());
                    }

                    @Override
                    public void onFailure(Call<MessageResponse<Article>> call, Throwable t) {
                        Log.e(TAG, "removeArticle@onFailure", t);
                    }
                });
        return liveData;
    }

    public MutableLiveData<MessageResponse<BannedArticle>> banArticle(String token, int id) {
        MutableLiveData<MessageResponse<BannedArticle>> liveData = new MutableLiveData<>();
        App.getAPI().banArticle("Bearer " + token, id)
                .enqueue(new Callback<MessageResponse<BannedArticle>>() {
                    @Override
                    public void onResponse(Call<MessageResponse<BannedArticle>> call,
                                           Response<MessageResponse<BannedArticle>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            liveData.postValue(response.body());
                        } else Log.e(TAG, "banArticle@onResponse #" + response.code());
                    }

                    @Override
                    public void onFailure(Call<MessageResponse<BannedArticle>> call, Throwable t) {
                        Log.e(TAG, "banArticle@onFailure", t);
                    }
                });

        return liveData;
    }
}
