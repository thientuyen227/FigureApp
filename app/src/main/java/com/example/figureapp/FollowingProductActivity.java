package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private ArrayList<FollowingProductModel> followingProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_product);
        setUpActionBar("Following Product");
        initData();
        initComponents();
        loadFollowingProducts();
    }
    private void loadFollowingProducts(){
        BaseAPIService.createService(IFollowingProductService.class).getAllFollowingProducts().enqueue(new Callback<ArrayList<FollowingProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<FollowingProductModel>> call, Response<ArrayList<FollowingProductModel>> response) {
                if (response.isSuccessful()){
                    followingProduct = response.body();
                    followingProductAdapter = new FollowingProductAdapter(followingProduct, FollowingProductActivity.this);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(FollowingProductActivity.this, 2);
                    rc_following_product.setLayoutManager(gridLayoutManager);
                    rc_following_product.setAdapter(followingProductAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<FollowingProductModel>> call, Throwable t) {
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