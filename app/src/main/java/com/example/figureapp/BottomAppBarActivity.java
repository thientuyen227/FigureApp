package com.example.figureapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.figureapp.R;
import com.example.figureapp.model.FollowingProductModel;
import com.example.figureapp.model.ProductModel;
import com.example.figureapp.model.User;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.ICartService;
import com.example.figureapp.service.IFollowingProductService;
import com.example.figureapp.service.IUserService;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomAppBarActivity extends AppCompatActivity {
    FloatingActionButton btnHome, btnCart,btnSetting, btnFollow;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";

    private static final String KEY_TOKEN = "keytoken";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_app_bar);
    }
    protected void setBottomAppBar(){
        btnHome = findViewById(R.id.btn_home);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BottomAppBarActivity.this, HomeActivity.class));
            }
        });
        btnCart = findViewById(R.id.btn_cart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BottomAppBarActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        btnFollow = findViewById(R.id.btn_follow);
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                String token = sharedPreferences.getString("token", ""); // Lấy token đã lưu trong SharedPreferences
                BaseAPIService.createService(IFollowingProductService.class).getAllFollowingProducts("Bearer " + token).enqueue(new Callback<ArrayList<ProductModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                        Intent intent = new Intent(BottomAppBarActivity.this, FollowingProductActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                        Toast.makeText(BottomAppBarActivity.this, "Đã có lỗi xảy ra. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnSetting = findViewById(R.id.btn_settings);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BottomAppBarActivity.this, SettingActivity.class));
            }
        });
    }

}
