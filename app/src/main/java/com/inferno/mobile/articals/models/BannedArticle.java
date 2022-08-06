package com.inferno.mobile.articals.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class BannedArticle {
    @SerializedName("id")
    private final Integer id;

    @SerializedName("ban_id")
    private final Integer banId;

    @SerializedName("artical_id")
    private final Integer articalId;

    @SerializedName("created_at")
    private final String createdAt;

    @SerializedName("updated_at")
    private final Date updatedAt;

    @SerializedName("artical")
    private final Article article;

    public BannedArticle(Integer id, Integer banId, Integer articalId,
                         String createdAt, Date updatedAt, Article article) {
        this.id = id;
        this.banId = banId;
        this.articalId = articalId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.article = article;
    }

    public Integer getId() {
        return id;
    }

    public Integer getBanId() {
        return banId;
    }

    public Integer getArticalId() {
        return articalId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Article getArticle() {
        return article;
    }
}
