package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    Button btnChangePassWord, btnChangeEmail, btnChangIDCard,btnReturn;
    TextView tvName;
    ImageView imUser;
    private void AnhXa()
    {
     btnChangePassWord = findViewById(R.id.btn_changePassWord);
     btnChangeEmail = findViewById(R.id.btn_changeEmail);
     btnChangIDCard = findViewById(R.id.btn_chaneIdCard);
     btnReturn = findViewById(R.id.btn_return);
     tvName = findViewById(R.id.tv_Name);
     imUser = findViewById(R.id.im_avatar);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        AnhXa();
        btnChangePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, Setting_password.class));
            }
        });

        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, Setting_profile.class));
            }
        });

        btnChangIDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, Setting_idcard.class));
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, HomeActivity.class));
            }
        });
    }
}