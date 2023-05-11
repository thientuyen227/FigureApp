package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.figureapp.adapter.ProductAdapter;
import com.example.figureapp.model.ProductModel;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.ICategoryService;
import com.example.figureapp.service.IProductService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends BaseActivity {
    ArrayList<ProductModel> products;
    private ProductAdapter productAdapter;
    private ImageView productImageView;
    private TextView productNameTextView;
    private TextView quantityTextView;
    private TextView descriptionTextView;
    private Button addToCartButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        setUpActionBar("Detail product");
        setBottomAppBar();
        initComponents();
        initData();
        Intent intent = getIntent();
        int idproduct = intent.getIntExtra("id", 0);
        loadProduct(idproduct);
    }
    private void loadProduct(int id){
        BaseAPIService.createService(IProductService.class).getDetailProduct(id).enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if (response.isSuccessful()) {
                    products = response.body();
                    ProductModel product = products.get(0);
                    Glide.with(DetailProductActivity.this).load(product.getImageProduct()).into(productImageView); // Load ảnh vào ImageView
                    productNameTextView.setText(product.getName()); // Set tên sản phẩm
                    descriptionTextView.setText(product.getDescription());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(DetailProductActivity.this, "Call Api Success", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initComponents() {
        productImageView = findViewById(R.id.product_image);
        productNameTextView = findViewById(R.id.product_name);
        descriptionTextView = findViewById(R.id.tv_descriptionProduct);
    }
    private void initData() {
        products = new ArrayList<>();
    }
}