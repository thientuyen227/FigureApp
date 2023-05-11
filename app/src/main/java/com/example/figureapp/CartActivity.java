package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
=======
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
>>>>>>> develop
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
<<<<<<< HEAD
=======
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
>>>>>>> develop
    ArrayList<ProductModel> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setUpActionBar("Cart");
        setBottomAppBar();
        initComponents();
        initData();
<<<<<<< HEAD
        Intent intent = getIntent();
        int iduser = intent.getIntExtra("iduser", 0);
        loadCart(iduser);
    }
    private void loadCart(int id){
        BaseAPIService.createService(ICartService.class).getAllProductInCart(id).enqueue(new Callback<ArrayList<ProductModel>>() {
=======
        loadCart();
    }
    private void loadCart(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String token =  sharedPreferences.getString(KEY_TOKEN, null);
        BaseAPIService.createService(ICartService.class).getAllProductInCart("Bearer " + token).enqueue(new Callback<ArrayList<ProductModel>>() {
>>>>>>> develop
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                products= response.body();
                productAdapter = new ProductAdapter(products, CartActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL,false);
                productRecyclerView.setLayoutManager(linearLayoutManager);
                productRecyclerView.setAdapter(productAdapter);
            }
<<<<<<< HEAD

=======
>>>>>>> develop
            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Call Api Success", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initComponents() {
<<<<<<< HEAD
        productRecyclerView = findViewById(R.id.product_recyclerview);
=======
        productRecyclerView = findViewById(R.id.rv_item_cart);
>>>>>>> develop
    }
    private void initData() {
        products = new ArrayList<>();
    }
}