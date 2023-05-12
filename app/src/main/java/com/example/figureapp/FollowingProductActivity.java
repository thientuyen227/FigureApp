package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.figureapp.adapter.FollowingProductAdapter;
import com.example.figureapp.adapter.ProductAdapter;
import com.example.figureapp.database.ProductDatabase;
import com.example.figureapp.entities.Products;
import com.example.figureapp.model.FollowingProductModel;
import com.example.figureapp.model.ProductModel;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.IFollowingProductService;
import com.example.figureapp.service.IProductService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingProductActivity extends BaseActivity implements ProductAdapter.iClickListener {
    private RecyclerView rc_following_product;
    private List<Products> listProduct;
    private FollowingProductAdapter followingProductAdapter;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
    private ArrayList<FollowingProductModel> followingProduct;
    private List<Products> productModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_product);
        setUpActionBar("Follow");
        setBottomAppBar();
        initData();
        initComponents();
        loadFollowingProducts();
    }
    private void loadFollowingProducts(){
        productModels = ProductDatabase.getInstance(getApplicationContext()).followingProductDao().getAll();
        followingProductAdapter = new FollowingProductAdapter(productModels, FollowingProductActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(FollowingProductActivity.this, 2);
        rc_following_product.setLayoutManager(gridLayoutManager);
        rc_following_product.setAdapter(followingProductAdapter);
    }
    private void initComponents() {
        rc_following_product = findViewById(R.id.rc_following_product);
    }
    private void initData() {
        followingProduct = new ArrayList<>();
    }

    @Override
    public void addProduct(Products product) {
    }
}