package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.figureapp.adapter.OrderAdapter;
import com.example.figureapp.adapter.ProductAdapter;
import com.example.figureapp.entities.Products;
import com.example.figureapp.model.OrderModel;
import com.example.figureapp.model.ProductModel;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.ICartService;
import com.example.figureapp.service.IOrderService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends BaseActivity implements ProductAdapter.iClickListener {
    private OrderAdapter orderAdapter;
    private RecyclerView productRecyclerView;

    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
    ArrayList<OrderModel> orderModels;
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
        BaseAPIService.createService(IOrderService.class).getAllOrderItem("Bearer " + token).enqueue(new Callback<ArrayList<OrderModel>>() {
            @Override
            public void onResponse(Call<ArrayList<OrderModel>> call, Response<ArrayList<OrderModel>> response) {
                orderModels= response.body();
                orderAdapter = new OrderAdapter(orderModels, OrderActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderActivity.this, LinearLayoutManager.VERTICAL,false);
                productRecyclerView.setLayoutManager(linearLayoutManager);
                productRecyclerView.setAdapter(orderAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<OrderModel>> call, Throwable t) {
                Toast.makeText(OrderActivity.this, "Đã có lỗi xảy ra. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initComponents() {
        productRecyclerView = findViewById(R.id.rc_order);
    }
    private void initData() {
        orderModels = new ArrayList<>();
    }

    @Override
    public void addProduct(Products product) {

    }
}