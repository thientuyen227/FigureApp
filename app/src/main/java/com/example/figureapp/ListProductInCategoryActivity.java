package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.figureapp.adapter.ProductAdapter;
import com.example.figureapp.model.ProductModel;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.ICategoryService;
import com.example.figureapp.service.IProductService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductInCategoryActivity extends BaseActivity {

    ArrayList<ProductModel> products;
    private ProductAdapter productAdapter;
    RecyclerView productRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product_in_category);
        setUpActionBar("Category");
        setBottomAppBar();
        initComponents();
        initData();
        Intent intent = getIntent();
        int idcategory = intent.getIntExtra("categoryId", 0);
        loadProduct(idcategory);
    }
    private void loadProduct(int id){
        BaseAPIService.createService(ICategoryService.class).getCategoryItem(id).enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if (response.isSuccessful()) {
                    products = response.body();
                    productAdapter = new ProductAdapter(products, ListProductInCategoryActivity.this);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ListProductInCategoryActivity.this, 2);
                    productRecyclerView.setLayoutManager(gridLayoutManager);
                    productRecyclerView.setAdapter(productAdapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(ListProductInCategoryActivity.this, "Call Api Success", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initComponents() {
        productRecyclerView = findViewById(R.id.rc_product);
    }
    private void initData() {
        products = new ArrayList<>();
    }
}