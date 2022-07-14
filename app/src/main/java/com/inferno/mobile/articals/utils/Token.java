package com.inferno.mobile.articals.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.inferno.mobile.articals.models.UserType;

public class Token {
    private final static String SHARED_NAME = "shared";
    private final static String LOGGED_IN = "logged_in";
    private final static String TOKEN = "token";
    private final static String TYPE = "type";

    public static boolean isLoggedIn(Context context) {
        return shared(context).getBoolean(LOGGED_IN, false);
    }

    public static void logIn(Context context, String token, String type) {
        sharedEdit(shared(context))
                .putString(TOKEN, token)
                .putString(TYPE, type)
                .putBoolean(LOGGED_IN, true)
                .apply();
    }

    public static String getToken(Context context) {
        return shared(context).getString(TOKEN, "");
    }


    public static UserType checkUserType(Context context) {
        return UserType.valueOf(shared(context).getString(TYPE, UserType.normal.toString()));
    }

    public static void logOut(Context context) {
        sharedEdit(shared(context))
                .remove(TOKEN)
                .remove(TYPE)
                .remove(LOGGED_IN)
                .apply();
    }

    private static SharedPreferences.Editor sharedEdit(SharedPreferences sharedPreferences) {
        return sharedPreferences.edit();
    }

    private static SharedPreferences shared(Context context) {
        return context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
    }
}
