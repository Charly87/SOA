package com.example.cotizaciondolar.services;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;
import java.util.HashMap;

public class SessionManager {
    /**
     * Sharedpref file name
     */
    private static final String PREF_NAME = "AndroidHivePref";
    private static final String IS_LOGGED_IN = "IsLoggedIn";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String LOGIN_DATE = "login_date";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String name, String email) {
        Date date = new Date();
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(USER_NAME, name);
        editor.putString(USER_EMAIL, email);
        editor.putString(LOGIN_DATE, date.toString());

        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();

        String userName = sharedPreferences.getString(USER_NAME, null);
        String userEmail = sharedPreferences.getString(USER_EMAIL, null);
        String loginDate = sharedPreferences.getString(LOGIN_DATE, null);

        user.put(USER_NAME, userName);
        user.put(USER_EMAIL, userEmail);
        user.put(LOGIN_DATE, loginDate);

        return user;
    }

    public boolean checkLogin() {
        String stringValue = sharedPreferences.getString(IS_LOGGED_IN, null);
        return Boolean.parseBoolean(stringValue);
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }
}
