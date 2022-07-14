package com.inferno.mobile.articals.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetMyArticleResponse {
    @SerializedName("code")
    private final int code;

    @SerializedName("msg")
    private final String message;

    @SerializedName("articles")
    private final ArrayList<Article> articles;

    public GetMyArticleResponse(int code, String message,  ArrayList<Article> articles) {
        this.code = code;
        this.message = message;
        this.articles = articles;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public  ArrayList<Article> getArticles() {
        return articles;
    }
}
