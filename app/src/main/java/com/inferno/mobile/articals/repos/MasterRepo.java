package com.inferno.mobile.articals.repos;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.inferno.mobile.articals.App;
import com.inferno.mobile.articals.models.Article;
import com.inferno.mobile.articals.models.ArticlesResponse;
import com.inferno.mobile.articals.models.GetMyArticleResponse;
import com.inferno.mobile.articals.models.MasterArticleResponse;
import com.inferno.mobile.articals.models.MessageResponse;
import com.inferno.mobile.articals.models.Reference;
import com.inferno.mobile.articals.models.User;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterRepo {
    private static MasterRepo instance;

    private final static String TAG = "MasterRepo";


    public static MasterRepo getInstance() {
        if (instance == null)
            instance = new MasterRepo();
        return instance;
    }

    private MasterRepo() {
    }

    public MutableLiveData<GetMyArticleResponse> getMyArticles(String token) {
        final MutableLiveData<GetMyArticleResponse> articlesLiveData = new MutableLiveData<>();

        App.getAPI().getMasterArticles("Bearer " + token)
                .enqueue(new Callback<GetMyArticleResponse>() {
                    @Override
                    public void onResponse(Call<GetMyArticleResponse> call,
                                           Response<GetMyArticleResponse> response) {
                        if (response.isSuccessful() && response.body() != null)
                            articlesLiveData.postValue(response.body());
                        else Log.e(TAG, "getMasterMyArticles@onResponse #" + response.code());
                    }

                    @Override
                    public void onFailure(Call<GetMyArticleResponse> call, Throwable t) {
                        Log.e(TAG, "getMyArticles@onFailure", t);
                    }
                });
        return articlesLiveData;
    }

    public MutableLiveData<ArrayList<User>> getDoctorOfField(String token) {
        MutableLiveData<ArrayList<User>> liveData = new MutableLiveData<>();
        App.getAPI().getDoctors("Bearer " + token)
                .enqueue(new Callback<ArrayList<User>>() {
                    @Override
                    public void onResponse(Call<ArrayList<User>> call,
                                           Response<ArrayList<User>> response) {
                        if (response.isSuccessful() && response.body() != null)
                            liveData.postValue(response.body());
                        else Log.e(TAG, "getDoctorOfField@onResponse #" + response.code());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                        Log.e(TAG, "getDoctorOfField@onFailure", t);
                    }
                });
        return liveData;
    }

    public MutableLiveData<MessageResponse<Article>> addArticle(String token, String name,
                                                                String univName, int doctorId,
                                                                File file,
                                                                ArrayList<Integer> refs) {
        MutableLiveData<MessageResponse<Article>> liveData = new MutableLiveData<>();

        RequestBody nameRequest = RequestBody.create(MediaType.parse("plan/text"), name);
        RequestBody[] refBodies = new RequestBody[refs.size()];
        for (int i = 0; i < refs.size(); i++) {
            refBodies[i] = RequestBody.create(MediaType.parse("plan/text"),
                    String.valueOf(refs.get(i)));
        }
        RequestBody univNameRequest = RequestBody.create(MediaType.parse("plan/text"), univName);
        RequestBody doctorIdRequest = RequestBody.create(MediaType.parse("plan/text"),
                String.valueOf(doctorId));
        RequestBody pdf = RequestBody.create(MediaType.parse("application/pdf"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part
                .createFormData("pdf", file.getName(), pdf);
        App.getAPI().addArticle("Bearer " + token, nameRequest, univNameRequest,
                        doctorIdRequest, refBodies, multipartBody)
                .enqueue(new Callback<MessageResponse<Article>>() {
                    @Override
                    public void onResponse(Call<MessageResponse<Article>> call,
                                           Response<MessageResponse<Article>> response) {
                        if (response.isSuccessful() && response.body() != null)
                            liveData.postValue(response.body());
                        else Log.e(TAG, "addArticle@onResponse #" + response.code());
                    }

                    @Override
                    public void onFailure(Call<MessageResponse<Article>> call,
                                          Throwable t) {
                        Log.e(TAG, "addArticle@onFailure", t);
                    }
                });

        return liveData;
    }

    public MutableLiveData<GetMyArticleResponse> getDoctorArticles(String token) {
        final MutableLiveData<GetMyArticleResponse> articlesLiveData = new MutableLiveData<>();
        App.getAPI().getDoctorArticles("Bearer " + token)
                .enqueue(new Callback<GetMyArticleResponse>() {
                    @Override
                    public void onResponse(Call<GetMyArticleResponse> call,
                                           Response<GetMyArticleResponse> response) {
                        if (response.isSuccessful() && response.body() != null)
                            articlesLiveData.postValue(response.body());
                        else Log.e(TAG, "getDoctorMyArticles@onResponse #" + response.code());
                    }

                    @Override
                    public void onFailure(Call<GetMyArticleResponse> call, Throwable t) {
                        Log.e(TAG, "getMyArticles@onFailure", t);
                    }
                });
        return articlesLiveData;
    }


    public MutableLiveData<MessageResponse<Article>> addDoctorArticle(String token, String name, String type,
                                                                      String univName, File file) {
        MutableLiveData<MessageResponse<Article>> liveData = new MutableLiveData<>();

        RequestBody nameRequest = RequestBody.create(MediaType.parse("plan/text"), name);
        RequestBody typeRequest = RequestBody.create(MediaType.parse("plan/text"), type);
        RequestBody univNameRequest = RequestBody.create(MediaType.parse("plan/text"), univName);
        RequestBody pdf = RequestBody.create(MediaType.parse("application/pdf"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part
                .createFormData("pdf", file.getName(), pdf);
        App.getAPI().addDoctorArticle("Bearer " + token, nameRequest,
                        typeRequest, univNameRequest, multipartBody)
                .enqueue(new Callback<MessageResponse<Article>>() {
                    @Override
                    public void onResponse(Call<MessageResponse<Article>> call,
                                           Response<MessageResponse<Article>> response) {
                        if (response.isSuccessful() && response.body() != null)
                            liveData.postValue(response.body());
                        else Log.e(TAG, "addArticle@onResponse #" + response.code());
                    }

                    @Override
                    public void onFailure(Call<MessageResponse<Article>> call,
                                          Throwable t) {
                        Log.e(TAG, "addArticle@onFailure", t);
                    }
                });

        return liveData;
    }

    public MutableLiveData<MessageResponse<Article>> removeArticle(String token, int id) {
        MutableLiveData<MessageResponse<Article>> liveData = new MutableLiveData<>();
        App.getAPI().removeMasterArticle("Bearer " +token, id)
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

    public MutableLiveData<ArrayList<Reference>> getReferences(String token) {
        MutableLiveData<ArrayList<Reference>> liveData = new MutableLiveData<>();
        App.getAPI().getReferences("Bearer " + token)
                .enqueue(new Callback<ArrayList<Reference>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Reference>> call,
                                           Response<ArrayList<Reference>> response) {
                        if (response.isSuccessful() && response.body() != null)
                            liveData.postValue(response.body());
                        else Log.e(TAG, "getReferences@onResponse #" + response.code());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Reference>> call, Throwable t) {
                        Log.e(TAG, "getReferences@onFailure", t);
                    }
                });
        return liveData;
    }
}
