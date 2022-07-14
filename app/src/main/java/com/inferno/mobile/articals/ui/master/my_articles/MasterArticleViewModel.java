package com.inferno.mobile.articals.ui.master.my_articles;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.articals.models.Article;
import com.inferno.mobile.articals.models.GetMyArticleResponse;
import com.inferno.mobile.articals.models.MessageResponse;
import com.inferno.mobile.articals.models.User;
import com.inferno.mobile.articals.repos.MasterRepo;

import java.io.File;
import java.util.ArrayList;

public class MasterArticleViewModel extends ViewModel {
    private MasterRepo masterRepo;

    public void init() {
        masterRepo = MasterRepo.getInstance();
    }

    public LiveData<GetMyArticleResponse> getMasterArticles(String token) {
         return masterRepo.getMyArticles(token);
    }

    public LiveData<GetMyArticleResponse> getDoctorArticles(String token){
        return masterRepo.getDoctorArticles(token);
    }

    public LiveData<ArrayList<User>> getDoctorOfField(String token) {
        return masterRepo.getDoctorOfField(token);
    }

    public LiveData<MessageResponse<Article>>addArticle(String token, String name, String type,
                                               String univName, int doctorId, File file){
        return masterRepo.addArticle(token, name, type, univName, doctorId, file);
    }
    public LiveData<MessageResponse<Article>> addDoctorArticle(String token, String name, String type,
                                                               String univName, File file){
        return masterRepo.addDoctorArticle(token, name, type, univName, file);
    }

}
