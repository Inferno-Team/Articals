package com.inferno.mobile.articals.ui.home;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.articals.models.Article;
import com.inferno.mobile.articals.models.ArticlesResponse;
import com.inferno.mobile.articals.repos.HomeRepo;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    private HomeRepo homeRepo;

    void init() {
        homeRepo = HomeRepo.getInstance();
    }

    LiveData<ArticlesResponse> getRecentArticles(String token,int pageNumber) {
        return homeRepo.getRecentArticles(token,pageNumber);
    }
}
