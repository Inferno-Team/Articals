package com.inferno.mobile.articals.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArticlesResponse {

    @SerializedName("data")
    private final ArrayList<ApprovedArticle> approvedArticles;

    @SerializedName("next_page_url")
    private final String nextPageUrl;

    @SerializedName("current_page")
    private final int currentPage;
    public ArrayList<ApprovedArticle> getApprovedArticles() {
        return approvedArticles;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public ArticlesResponse(ArrayList<ApprovedArticle> data, String nextPageUrl, int currentPage) {
        this.approvedArticles = data;
        this.nextPageUrl = nextPageUrl;
        this.currentPage = currentPage;
    }

    public ArrayList<Article>getArticles(){
        ArrayList<Article>articles = new ArrayList<>();
        for (ApprovedArticle article:approvedArticles)
            articles.add(article.getArticle());
        return articles;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}

