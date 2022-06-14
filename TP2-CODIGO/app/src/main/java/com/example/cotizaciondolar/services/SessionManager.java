package com.example.cotizaciondolar.services;

import android.content.Context;
import android.content.SharedPreferences;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;

public class SessionManager {
    // Nombre del archivo de shared preferences
    private static final String PREF_NAME = "AndroidHivePref";

    private static final String IS_LOGGED_IN = "is_logged_in";
    public static final String USER_EMAIL = "user_email";
    public static final String TOKEN_CREATED_DATE = "token_created_date";
    public static final String TOKEN = "token";
    public static final String TOKEN_REFRESH = "token_refresh";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String email, String token, String tokenRefresh) {
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("UTC-3"));
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(USER_EMAIL, email);
        editor.putString(TOKEN, token);
        editor.putString(TOKEN_REFRESH, tokenRefresh);
        editor.putString(TOKEN_CREATED_DATE, dateTime.toString());

        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> userDetails = new HashMap<>();

        String userEmail = sharedPreferences.getString(USER_EMAIL, null);
        String loginDate = sharedPreferences.getString(TOKEN_CREATED_DATE, null);
        String token = sharedPreferences.getString(TOKEN, null);
        String tokenRefresh = sharedPreferences.getString(TOKEN_REFRESH, null);

        userDetails.put(USER_EMAIL, userEmail);
        userDetails.put(TOKEN_CREATED_DATE, loginDate);
        userDetails.put(TOKEN, token);
        userDetails.put(TOKEN_REFRESH, tokenRefresh);

        return userDetails;
    }
    
    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }
}
