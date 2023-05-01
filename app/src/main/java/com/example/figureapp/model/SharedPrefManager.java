package com.example.figureapp.model;

import static android.accounts.AccountManager.KEY_PASSWORD;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.figureapp.LoginActivity;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";

    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_TOKEN = "keytoken";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_PASSWORD = "keypassword";
    private static SharedPrefManager mInstance;
    private static Context ctx;
    //khỏi tạo constructor
    private SharedPrefManager(Context context) {
        ctx=context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
        mInstance = new SharedPrefManager (context);
        }
        return mInstance;
    }
    public void userLogin (User user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences (SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences. Editor editor = sharedPreferences.edit();
        editor.putString (KEY_USERNAME, user.getName());
        editor.putString (KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.apply();
    }
    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences (SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString (KEY_USERNAME,null) != null;
    }
    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences (SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_USERNAME,null),
                sharedPreferences.getString (KEY_EMAIL,  null),
                sharedPreferences.getString (KEY_PASSWORD, null)
        );
    }
    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences (SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences. Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, LoginActivity.class));}
    public void saveToken(String token) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public String getToken() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sharedPreferences.getString(KEY_TOKEN, "");
    }

}
