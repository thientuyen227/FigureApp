package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.figureapp.adapter.CartAdapter;
import com.example.figureapp.adapter.FollowingProductAdapter;
import com.example.figureapp.adapter.ProductAdapter;
import com.example.figureapp.database.CartDatabase;
import com.example.figureapp.database.ProductDatabase;
import com.example.figureapp.entities.Cart;
import com.example.figureapp.entities.Products;
import com.example.figureapp.model.CartModel;
import com.example.figureapp.model.ProductModel;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.ICartService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends BaseActivity implements ProductAdapter.iClickListener {
    private CartAdapter cartAdapter;
    private RecyclerView productRecyclerView;
    private TextView total_amount;
    private Button btnCheckout;

    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
    ArrayList<ProductModel> products;
    private List<Cart> carts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setUpActionBar("Cart");
        setBottomAppBar();
        initComponents();
        initData();
        loadCart();
    }
    private void loadCart(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String token =  sharedPreferences.getString(KEY_TOKEN, null);
        carts = CartDatabase.getInstance(getApplicationContext()).cartDao().getAll();
        cartAdapter = new CartAdapter(carts, CartActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL,false);
        productRecyclerView.setLayoutManager(linearLayoutManager);
        productRecyclerView.setAdapter(cartAdapter);
        CartModel cartModel = new CartModel();
        List<Integer> cartList = CartDatabase.getInstance(getApplicationContext()).cartDao().getAllIdProduct();
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseAPIService.createService(ICartService.class).checkOutCart(cartList,"Bearer " + token).enqueue(new Callback<List<Cart>>() {
                    @Override
                    public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                        carts= response.body();
                        cartAdapter = new CartAdapter(carts, CartActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL,false);
                        productRecyclerView.setLayoutManager(linearLayoutManager);
                        productRecyclerView.setAdapter(cartAdapter);
                    }
                    @Override
                    public void onFailure(Call<List<Cart>> call, Throwable t) {
                        Toast.makeText(CartActivity.this, "Đã có lỗi xảy ra. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

//
    }
    private void initComponents() {
        productRecyclerView = findViewById(R.id.rv_item_cart);
        btnCheckout = findViewById(R.id.btnCheckout);
        total_amount = findViewById(R.id.total_amount);
    }
    private void initData() {
        products = new ArrayList<>();
    }

    @Override
    public void addProduct(Products product) {

    }
}