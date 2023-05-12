package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.figureapp.adapter.ProductAdapter;
import com.example.figureapp.entities.Products;
import com.example.figureapp.model.ProductModel;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.ICartService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends BaseActivity implements ProductAdapter.iClickListener {
    private ProductAdapter productAdapter;
    private RecyclerView productRecyclerView;

    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
    ArrayList<ProductModel> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setUpActionBar("Order");
        setBottomAppBar();
        initComponents();
        initData();
        loadOrder();
    }
    private void loadOrder(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String token =  sharedPreferences.getString(KEY_TOKEN, null);
        BaseAPIService.createService(ICartService.class).getAllProductInCart("Bearer " + token).enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                products= response.body();
                productAdapter = new ProductAdapter(products, OrderActivity.this, OrderActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderActivity.this, LinearLayoutManager.VERTICAL,false);
                productRecyclerView.setLayoutManager(linearLayoutManager);
                productRecyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(OrderActivity.this, "Đã có lỗi xảy ra. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initComponents() {
        productRecyclerView = findViewById(R.id.product_recyclerview);
    }
    private void initData() {
        products = new ArrayList<>();
    }

    @Override
    public void addProduct(Products product) {

    }
}