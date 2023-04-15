package com.example.figureapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    ImageView btnProfile, btnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("FigureApp");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        btnProfile = new ImageView(this);
        btnCart = new ImageView(this);
        btnProfile.setImageResource(R.drawable.ic_profile);
        btnCart.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
        LinearLayout customView = new LinearLayout(this);
        customView.setOrientation(LinearLayout.HORIZONTAL);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT,
                Gravity.END | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 20; // khoảng cách giữa ic_profile và ic_cart
        customView.addView(btnProfile);
        customView.addView(btnCart);
        actionBar.setCustomView(customView, layoutParams);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, ProfileActivity.class));
            }
        });
    }
}