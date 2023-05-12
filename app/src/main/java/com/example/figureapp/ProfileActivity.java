package com.example.figureapp;

import android.content.Context;
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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {
    TextView tv_name, tv_IdCard, tv_email, tv_eWallet;
    ArrayList<User> users;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
    Button btn_logout;
    Context context;
    ImageView im_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setBottomAppBar();
        setUpActionBar("Profile");
        initComponents();
        initData();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            String token =  sharedPreferences.getString(KEY_TOKEN, null);
            BaseAPIService.createService(IUserService.class).getProfile("Bearer "+token).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    updateProfile(response.body());
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, "Đã có lỗi xảy ra. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    public void onClick(View view) {
        if (view.equals(btn_logout)) {
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }
    }
    private void updateProfile(User user){
        Glide.with(getApplicationContext()).load(user.getAvatar()).into(im_profile);
        tv_name.setText(user.getName());
        tv_email.setText(user.getEmail());
        tv_IdCard.setText(user.getIdCard());
        tv_eWallet.setText(Integer.toString(user.getEWallet()));
    }
    private void initComponents() {
        tv_IdCard = findViewById(R.id.tv_IdCard);
        btn_logout = findViewById(R.id.btn_logout);
        tv_name = findViewById(R.id.tv_Name);
        tv_email = findViewById(R.id.tv_Gmail);
        tv_eWallet = findViewById(R.id.tv_eWallet);
        btn_logout = findViewById(R.id.btn_logout);
        im_profile = findViewById(R.id.im_avatar);
        btn_logout.setOnClickListener(this);
    }
    private void initData() {

    }
}