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
import com.example.figureapp.model.SharedPrefManager;
import com.example.figureapp.model.User;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.IUserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends BaseActivity {

    Button btnChangePassWord, btnChangeProfile, btnChangeIDCard,btnOrder;
    TextView tvName;
    ImageView imUser;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
    private void initComponents()
    {btnOrder = findViewById(R.id.btn_order);
     btnChangePassWord = findViewById(R.id.btn_changePassWord);
     btnChangeProfile = findViewById(R.id.btn_changeProfile);
     btnChangeIDCard = findViewById(R.id.btn_changeIdCard);
     tvName = findViewById(R.id.tv_name);
     imUser = findViewById(R.id.im_avatar);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setUpActionBar("Setting");
        setBottomAppBar();
        initComponents();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            String token =  sharedPreferences.getString(KEY_TOKEN, null);
            BaseAPIService.createService(IUserService.class).getProfile("Bearer "+token).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    setProfile(response.body());
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(SettingActivity.this, "Đã có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        btnChangePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, Setting_password.class));
            }
        });

        btnChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, Setting_profile.class));
            }
        });

        btnChangeIDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, Setting_idcard.class));
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, OrderActivity.class));
            }
        });
    }
    private void setProfile(User user){
        Glide.with(getApplicationContext()).load(user.getAvatar()).into(imUser);
        tvName.setText(user.getName().toString().trim());
    }
}