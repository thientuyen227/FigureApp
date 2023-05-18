package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.figureapp.adapter.admin.ProductAdapter;
import com.example.figureapp.model.CategoryModel;
import com.example.figureapp.model.ProductModel;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.IProductService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends BaseActivity {
    RecyclerView rc_product;
    Button btnAdd;
    ArrayList<ProductModel> productModels;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setUpActionBar("Products");
        initComponents();
        initData();
        loadProducts();
    }
    private void loadProducts(){

        BaseAPIService.createService(IProductService.class).getAllProducts().enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                productModels = response.body();
                productAdapter = new ProductAdapter(productModels, ProductActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductActivity.this, LinearLayoutManager.VERTICAL, false);
                rc_product.setLayoutManager(linearLayoutManager);
                rc_product.setAdapter(productAdapter);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ProductActivity.this, AddProductActivity.class));
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {

            }
        });
    }
    private void initComponents(){
        rc_product = findViewById(R.id.rc_product);
        btnAdd = findViewById(R.id.btnAdd);
    }
    private void initData(){}
}