package com.example.figureapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.figureapp.model.User;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.IUserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseActivity extends BottomAppBarActivity {

    ImageView btnProfile;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    protected void setUpActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.colorPrimary));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        btnProfile = new ImageView(this);
        btnProfile.setImageResource(R.drawable.ic_profile);
        LinearLayout customView = new LinearLayout(this);
        customView.setOrientation(LinearLayout.HORIZONTAL);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT,
                Gravity.END | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 20;
        customView.addView(btnProfile);
        actionBar.setCustomView(customView, layoutParams);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String token = sharedPreferences.getString(KEY_TOKEN, "");
        BaseAPIService.createService(IUserService.class).getProfile("Bearer "+ token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user= response.body();
                Glide.with(getApplicationContext()).load(user.getAvatar()).into(btnProfile);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(BaseActivity.this, "Đã có lỗi xảy ra. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, ProfileActivity.class));
            }
        });
    }

}