package com.inferno.mobile.articals.repos;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inferno.mobile.articals.App;
import com.inferno.mobile.articals.models.Article;
import com.inferno.mobile.articals.models.ArticlesResponse;
import com.inferno.mobile.articals.models.MessageResponse;
import com.inferno.mobile.articals.models.Report;

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

    public MutableLiveData<ArrayList<Article>> getRecentArticles(String token) {
        MutableLiveData<ArrayList<Article>> liveData = new MutableLiveData<>();
        App.getAPI().getRecentArticles("Bearer " + token)
                .enqueue(new Callback<ArrayList<Article>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Article>> call,
                                           Response<ArrayList<Article>> response) {
                        if (response.isSuccessful() && response.body() != null)
                            liveData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Article>> call, Throwable t) {
                        Log.e("TAG", "onFailure", t);
                    }
                });
        return liveData;
    }

    public MutableLiveData<Article> getArticleDetails(String token, int id) {
        MutableLiveData<Article> liveData = new MutableLiveData<>();
        App.getAPI().getArticleDetails("Bearer " + token, id)
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
        App.getAPI().downloadPDF("Bearer " + token, id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
            }
        });
    }

    public MutableLiveData<MessageResponse<Report>> reportArticle(String token,
                                                                  String report,
                                                                  int articleId) {
        MutableLiveData<MessageResponse<Report>> liveData = new MutableLiveData<>();
        App.getAPI()
                .makeReport("Bearer " + token, report, articleId)
                .enqueue(new Callback<MessageResponse<Report>>() {
                    @Override
                    public void onResponse(Call<MessageResponse<Report>> call,
                                           Response<MessageResponse<Report>> response) {
                        if (response.isSuccessful() && response.body() != null)
                            liveData.postValue(response.body());
                        else Log.e(TAG, "reportArticle@onResponse #" + response.code());
                    }

                    @Override
                    public void onFailure(Call<MessageResponse<Report>> call, Throwable t) {
                        Log.e(TAG, "reportArticle#onFailure", t);
                    }
                });
        return liveData;
    }

    public MutableLiveData<MessageResponse<Report>> reportComment(String token,
                                                                  String report,
                                                                  int commentId) {
        MutableLiveData<MessageResponse<Report>> liveData = new MutableLiveData<>();
        App.getAPI()
                .makeReportAboutComment("Bearer " + token, report, commentId)
                .enqueue(new Callback<MessageResponse<Report>>() {
                    @Override
                    public void onResponse(Call<MessageResponse<Report>> call,
                                           Response<MessageResponse<Report>> response) {
                        if (response.isSuccessful() && response.body() != null)
                            liveData.postValue(response.body());
                        else Log.e(TAG, "reportComment@onResponse #" + response.code());
                    }

                    @Override
                    public void onFailure(Call<MessageResponse<Report>> call, Throwable t) {
                        Log.e(TAG, "reportComment#onFailure", t);
                    }
                });
        return liveData;
    }


    public MutableLiveData<ArrayList<Article>> searchArticles(String name, String token) {
        MutableLiveData<ArrayList<Article>> liveData = new MutableLiveData<>();
        App.getAPI()
                .searchArticle("Bearer " + token, name)
                .enqueue(new Callback<ArrayList<Article>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Article>> call,
                                           Response<ArrayList<Article>> response) {
                        if (response.isSuccessful() && response.body() != null)
                            liveData.postValue(response.body());
                        else Log.e(TAG, "searchArticles@onResponse #" + response.code());

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Article>> call, Throwable t) {
                        Log.e(TAG, "searchArticles#onFailure", t);
                    }
                });
        return liveData;
    }
}
