package com.inferno.mobile.articals.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MasterArticleResponse {
    @SerializedName("data")
    private final ArrayList<Article> approvedArticles;

    @SerializedName("next_page_url")
    private final String nextPageUrl;

    public ArrayList<Article> getApprovedArticles() {
        return approvedArticles;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public MasterArticleResponse(ArrayList<Article> data, String nextPageUrl) {
        this.approvedArticles = data;
        this.nextPageUrl = nextPageUrl;
    }

}
