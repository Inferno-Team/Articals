package com.inferno.mobile.articals.repos;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.inferno.mobile.articals.App;
import com.inferno.mobile.articals.models.Article;
import com.inferno.mobile.articals.models.ArticlesResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepo {
    private static HomeRepo instance;
    private static final String TAG = HomeRepo.class.toString();

    public static HomeRepo getInstance() {
        if (instance == null)
            instance = new HomeRepo();

        return instance;
    }

    private HomeRepo() {
    }

    public MutableLiveData<ArticlesResponse> getRecentArticles(String token,int pageNumber) {
        MutableLiveData<ArticlesResponse> liveData = new MutableLiveData<>();
        App.getAPI().getRecentArticles("Bearer " + token,pageNumber)
                .enqueue(new Callback<ArticlesResponse>() {
                    @Override
                    public void onResponse(Call<ArticlesResponse> call,
                                           Response<ArticlesResponse> response) {
                        if (response.isSuccessful() && response.body() != null)
                            liveData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<ArticlesResponse> call, Throwable t) {
                        Log.e("TAG", "onFailure", t);
                    }
                });
        return liveData;
    }

    public MutableLiveData<Article> getArticleDetails(String token, int id) {
        MutableLiveData<Article> liveData = new MutableLiveData<>();
        App.getAPI().getArticleDetails("Bearer "+token, id)
                .enqueue(new Callback<Article>() {
                    @Override
                    public void onResponse(Call<Article> call, Response<Article> response) {
                        liveData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<Article> call, Throwable t) {
                        Log.e(TAG, "getArticleDetails#onFailure", t);
                    }
                });
        return liveData;
    }

    public void downloadPDF(String token, int id) {
        App.getAPI().downloadPDF("Bearer "+token, id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
            }
        });
    }
}
