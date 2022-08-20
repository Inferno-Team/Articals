package com.inferno.mobile.articals.ui.home;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.articals.models.Article;
import com.inferno.mobile.articals.models.ArticlesResponse;
import com.inferno.mobile.articals.models.Comment;
import com.inferno.mobile.articals.models.MessageResponse;
import com.inferno.mobile.articals.models.Report;
import com.inferno.mobile.articals.repos.HomeRepo;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    private HomeRepo homeRepo;

    void init() {
        homeRepo = HomeRepo.getInstance();
    }

    LiveData<ArrayList<Article>> getRecentArticles(String token) {
        return homeRepo.getRecentArticles(token);
    }

    public LiveData<MessageResponse<Report>> reportArticle
            (String token, String report, int articleId) {
        return homeRepo.reportArticle(token, report, articleId);
    }

    public LiveData<MessageResponse<Report>> reportComment
            (String token, String report, int articleId) {
        return homeRepo.reportComment(token, report, articleId);
    }

    public LiveData<ArrayList<Article>> searchArticles(String name, String token) {
        return homeRepo.searchArticles(name, token);
    }

    public MutableLiveData<MessageResponse<Comment>> addComment(String token, String comment, int id) {
        return homeRepo.addComment(token, comment, id);
    }
}
