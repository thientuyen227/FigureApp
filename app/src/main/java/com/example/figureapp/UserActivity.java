package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.figureapp.adapter.UserAdapter;
import com.example.figureapp.model.User;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.IUserService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends BaseActivity {
    RecyclerView userRecyclerView;
    ArrayList<User> users;
    UserAdapter userAdapter;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setUpActionBar("Users");
        initComponents();
        initData();
        loadUser();
    }
    private void loadUser(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String token =  sharedPreferences.getString(KEY_TOKEN, null);
        BaseAPIService.createService(IUserService.class).getAllUser("Bearer " + token).enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                users = response.body();
                userAdapter = new UserAdapter(users, UserActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserActivity.this
                        , LinearLayoutManager.VERTICAL, false);
                userRecyclerView.setLayoutManager(linearLayoutManager);
                userRecyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(UserActivity.this, "Lỗi không xác định", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initComponents() {
        userRecyclerView = findViewById(R.id.rc_user);
    }
    private void initData() {
        users = new ArrayList<>();
    }
}