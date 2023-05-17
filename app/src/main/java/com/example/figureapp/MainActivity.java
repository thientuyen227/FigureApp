package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.figureapp.model.User;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.IUserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    ImageView imAvatar;
    TextView tvName;
    Button btnUsers;
    Button btnProducts;
    Button btnCategories;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpActionBar("Figure app");
        initComponents();
        initData();
        load();
        btnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserActivity.class));
            }
        });
    }
    private void load(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String token =  sharedPreferences.getString(KEY_TOKEN, null);
        BaseAPIService.createService(IUserService.class).getProfile("Bearer " + token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if(!user.getRole().equals("admin")){
                    Toast.makeText(MainActivity.this, "Bạn không đủ quyền để truy cập", Toast.LENGTH_SHORT).show();
                }
                else {
                    Glide.with(getApplicationContext()).load(user.getAvatar()).into(imAvatar);
                    tvName.setText(user.getName());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
    private void initComponents(){
        imAvatar= findViewById(R.id.im_avatar);
        tvName = findViewById(R.id.tvName);
        btnUsers = findViewById(R.id.btnUser);
        btnCategories = findViewById(R.id.btnCategory);
        btnProducts = findViewById(R.id.btnProduct);
    }
    private void initData(){

    }
}