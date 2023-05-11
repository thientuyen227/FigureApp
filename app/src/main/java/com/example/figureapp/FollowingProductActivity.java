package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.figureapp.adapter.FollowingProductAdapter;
import com.example.figureapp.adapter.ProductAdapter;
import com.example.figureapp.model.FollowingProductModel;
import com.example.figureapp.model.ProductModel;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.IFollowingProductService;
import com.example.figureapp.service.IProductService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingProductActivity extends BaseActivity {
    private RecyclerView rc_following_product;
    private FollowingProductAdapter followingProductAdapter;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
    private ArrayList<FollowingProductModel> followingProduct;
    private  ProductAdapter productAdapter;
    private ArrayList<ProductModel> productModels;
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
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String token = sharedPreferences.getString(KEY_TOKEN, null); // Lấy token đã lưu trong SharedPreferences
        System.out.println((token));
        BaseAPIService.createService(IFollowingProductService.class).getAllFollowingProducts("Bearer " + token).enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if (response.isSuccessful()){
                    productModels = response.body();
                    System.out.println(productModels);
                    productAdapter = new ProductAdapter(productModels, FollowingProductActivity.this);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(FollowingProductActivity.this, 2);
                    rc_following_product.setLayoutManager(gridLayoutManager);
                    rc_following_product.setAdapter(productAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Log.d("error", t.getMessage());
            }
        });
    }
    private void initComponents() {
        rc_following_product = findViewById(R.id.rc_following_product);
    }
    private void initData() {
        followingProduct = new ArrayList<>();
    }
}