package com.example.figureapp;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.figureapp.adapter.CategoryAdapter;
import com.example.figureapp.adapter.OnItemClickListener;
import com.example.figureapp.adapter.ProductAdapter;
import com.example.figureapp.model.CategoryModel;
import com.example.figureapp.model.ProductModel;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.ICategoryService;
import com.example.figureapp.service.IProductService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity {
    final String TAG = HomeActivity.class.getName();
    ArrayList<CategoryModel> categories;
    ArrayList<ProductModel> products;
    private ProductAdapter productAdapter;

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
                    categoryAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position, View view) {
                            Log.d("TAG", "Hello");
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable t) {
                Log.d("error", t.getMessage());
            }
        });
    }
    private void loadProducts(){
        BaseAPIService.createService(IProductService.class).getAllProducts().enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if (response.isSuccessful()){
                    products = response.body();
                    productAdapter = new ProductAdapter(products, HomeActivity.this);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(HomeActivity.this, 2);
                    productRecyclerView.setLayoutManager(gridLayoutManager);
                    productRecyclerView.setAdapter(productAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Log.d("error", t.getMessage());
            }
        });
    }
    private void initComponents() {
        categoryRecyclerView = findViewById(R.id.category_recyclerview);
        productRecyclerView = findViewById(R.id.product_recyclerview);
    }
    private void initData() {
        categories = new ArrayList<>();
        products = new ArrayList<>();
    }

}

