package com.inferno.mobile.articals.ui.article_details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.inferno.mobile.articals.models.Article;
import com.inferno.mobile.articals.repos.HomeRepo;

public class ArticleViewModel extends ViewModel {
    private HomeRepo homeRepo;
     void init(){
         homeRepo = HomeRepo.getInstance();
     }
     LiveData<Article> getArticleDetails(String token,int id){
         return homeRepo.getArticleDetails(token, id);
     }
     void downloadPDF(String token,int id){
         homeRepo.downloadPDF(token, id);
     }
}
