package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.figureapp.model.SharedPrefManager;
import com.example.figureapp.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileActivity extends BaseActivity {
    TextView tv_name, tv_id, tv_email, tv_gender;
    Button btn_logout;
    ImageView im_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setBottomAppBar();
        setUpActionBar("Profile");
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            tv_id = findViewById(R.id.tv_Id);
            tv_name = findViewById(R.id.tv_Name);
            tv_email = findViewById(R.id.tv_Gmail);
            tv_gender = findViewById(R.id.tv_Gender);
            btn_logout = findViewById(R.id.btn_logout);
            im_profile = findViewById(R.id.im_avatar);
            User user = SharedPrefManager.getInstance(this).getUser();
            //tv_id.setText(String.valueOf(user.get));
            tv_name.setText(user.getName());
            tv_email.setText(user.getEmail());
            //gender.setText(user.getGender());
            //Glide.with(getApplicationContext()).load(user.getImages()).into(imageViewporfile);
            btn_logout.setOnClickListener(this::onClick);

            //imageViewporfile.setClickable(true);
//            imageViewporfile.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(ProfilesActivity.this, ProfilesActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            });
//        } else {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
        }
    }
    public void onClick(View view) {
        if (view.equals(btn_logout)) {
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }
    }
}