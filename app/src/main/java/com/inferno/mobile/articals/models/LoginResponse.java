package com.inferno.mobile.articals.models;

public class LoginResponse {
    private final String message;
    private final int code;
    private final String token;
    private final UserType type;

    public LoginResponse(String message, int code, String token, UserType type) {
        this.message = message;
        this.code = code;
        this.token = token;
        this.type = type;
    }

    public UserType getType() {
        return type;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
