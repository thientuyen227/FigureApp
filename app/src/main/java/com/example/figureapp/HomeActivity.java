package com.example.figureapp;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.figureapp.adapter.CategoryAdapter;
import com.example.figureapp.adapter.OnItemClickListener;
import com.example.figureapp.adapter.ProductAdapter;
import com.example.figureapp.dao.FollowingProductDao;
import com.example.figureapp.database.ProductDatabase;
import com.example.figureapp.entities.Products;
import com.example.figureapp.model.CategoryModel;
import com.example.figureapp.model.ProductModel;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.ICategoryService;
import com.example.figureapp.service.IProductService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity implements ProductAdapter.iClickListener {
    final String TAG = HomeActivity.class.getName();
    ArrayList<CategoryModel> categories;
    ArrayList<ProductModel> products;
    private ProductAdapter productAdapter;
    private SearchView btnSearch;

    private CategoryAdapter categoryAdapter;
    RecyclerView categoryRecyclerView, productRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpActionBar("FigureApp");
        setBottomAppBar();
        initComponents();
        initData();
        loadCategories();
        loadProducts();
        btnSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    searchProduct(query);
                }
                else {

                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private void loadCategories() {
        BaseAPIService.createService(ICategoryService.class).getAllCategories().enqueue(new Callback<ArrayList<CategoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {
                if (response.isSuccessful()) {
                    categories = response.body();
                    categoryAdapter = new CategoryAdapter(categories, HomeActivity.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    categoryRecyclerView.setLayoutManager(linearLayoutManager);
                    categoryRecyclerView.setAdapter(categoryAdapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Đã có lỗi xảy ra. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadProducts(){
        BaseAPIService.createService(IProductService.class).getAllProducts().enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if (response.isSuccessful()){
                    products = response.body();
                    productAdapter = new ProductAdapter(products, HomeActivity.this, HomeActivity.this);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(HomeActivity.this, 2);
                    productRecyclerView.setLayoutManager(gridLayoutManager);
                    productRecyclerView.setAdapter(productAdapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Đã có lỗi xảy ra. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void searchProduct(String keyWord){
        BaseAPIService.createService(IProductService.class).searchProduct(keyWord).enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                products = response.body();
                productAdapter.updateProducts(products);
                System.out.println(products);
                if(products.isEmpty()){
                    Toast.makeText(HomeActivity.this, "Không tìm thấy sản phẩm cần tìm", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Đã có lỗi xảy ra. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initComponents() {
        categoryRecyclerView = findViewById(R.id.category_recyclerview);
        productRecyclerView = findViewById(R.id.product_recyclerview);
        btnSearch = findViewById(R.id.searchView);
    }
    private void initData() {
        categories = new ArrayList<>();
        products = new ArrayList<>();
    }

    @Override
    public void addProduct(Products product) {
    }
}

