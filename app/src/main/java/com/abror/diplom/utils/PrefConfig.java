package com.abror.diplom.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefConfig {
    private static final String LOGIN_STATUS = "login_status";
    private static final String USER_LOGIN = "user_login";
    private static final String USER_ID = "user_id";

    private final static String FILE_NAME = "preferences";
    private SharedPreferences preferences;
    private Context context;

    public PrefConfig(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(FILE_NAME, 0);
    }

    private SharedPreferences.Editor getEditor() {
        return preferences.edit();
    }

    // login status
    public void setLoginStatus(boolean status) {
        getEditor().putBoolean(LOGIN_STATUS, status).commit();
    }
    public boolean getLoginStatus() {
        return preferences.getBoolean(LOGIN_STATUS, false);
    }

    // user login
    public void setUserLogin(String login) {
        getEditor().putString(USER_LOGIN, login).commit();
    }
    public String getUserLogin() {
        return preferences.getString(USER_LOGIN, "");
    }

    // user id
    public void setUserId(int id) {
        getEditor().putInt(USER_ID, id).commit();
    }
    public int getUserId() {
        return preferences.getInt(USER_ID, -1);
    }
}
