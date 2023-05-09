package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.figureapp.adapter.ProductAdapter;
import com.example.figureapp.model.CartModel;
import com.example.figureapp.model.ProductModel;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.ICartService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends BaseActivity {
    private ProductAdapter productAdapter;
    private RecyclerView productRecyclerView;
    ArrayList<ProductModel> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setUpActionBar("Cart");
        setBottomAppBar();
        initComponents();
        initData();
        Intent intent = getIntent();
        int iduser = intent.getIntExtra("iduser", 0);
        loadCart(iduser);
    }
    private void loadCart(int id){
        BaseAPIService.createService(ICartService.class).getAllProductInCart(id).enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                products= response.body();
                productAdapter = new ProductAdapter(products, CartActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL,false);
                productRecyclerView.setLayoutManager(linearLayoutManager);
                productRecyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Call Api Success", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initComponents() {
        productRecyclerView = findViewById(R.id.product_recyclerview);
    }
    private void initData() {
        products = new ArrayList<>();
    }
}