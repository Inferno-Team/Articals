package com.inferno.mobile.articals.models;

import com.google.gson.annotations.SerializedName;

public class MessageResponse<T> {

    @SerializedName("code")
    private final int code;
    @SerializedName("message")
    private final String message;
    @SerializedName("data")
    private final T data;

    public MessageResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
