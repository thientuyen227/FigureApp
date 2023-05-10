package com.example.figureapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.figureapp.model.SharedPrefManager;
import com.example.figureapp.model.User;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.IUserService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseActivity {
    TextView tv_name, tv_IdCard, tv_email, tv_eWallet;
    ArrayList<User> users;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
    Button btn_logout;
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
            User user1= SharedPrefManager.getInstance(this).getUser();
            System.out.println(user1);
            BaseAPIService.createService(IUserService.class).profile(user1.getName(),user1.getEmail(), user1.getAvatar(), user1.getIdCard(), user1.getEWallet(),token).enqueue(new Callback<ArrayList<User>>() {
                @Override
                public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                    users= response.body();
                    updateProfile(user1);
                }
                @Override
                public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, "Call Api Success", Toast.LENGTH_LONG).show();
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
        tv_name.setText(user.getName());
        tv_email.setText(user.getEmail());
        tv_IdCard.setText(user.getIdCard());
        tv_eWallet.setText(Integer.toString(user.getEWallet()));

    }
    private void initComponents() {
        tv_IdCard = findViewById(R.id.tv_IdCard);
        tv_name = findViewById(R.id.tv_Name);
        tv_email = findViewById(R.id.tv_Gmail);
        tv_eWallet = findViewById(R.id.tv_eWallet);
        btn_logout = findViewById(R.id.btn_logout);
        im_profile = findViewById(R.id.im_avatar);
    }
    private void initData() {
    }
}