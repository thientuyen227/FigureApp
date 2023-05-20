package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.figureapp.model.ProductModel;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.IProductService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends BaseActivity {
    EditText edtName, edtIdCategory, edtPrice, edtDescription, edtQuantity;
    Button btnAdd;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        setUpActionBar("Add product");
        initComponents();
        initData();;
        loadProduct();
    }
    private void loadProduct(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String token =  sharedPreferences.getString(KEY_TOKEN, null);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductModel productModel = addProduct();
                BaseAPIService.createService(IProductService.class).addProduct("Bearer " + token, productModel.getName(), productModel.getIdCategory(),
                        productModel.getDescription(), productModel.getPrice(),productModel.getQuantity()).enqueue(new Callback<ProductModel>() {
                    @Override
                    public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                        Toast.makeText(AddProductActivity.this, "Add product thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddProductActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onFailure(Call<ProductModel> call, Throwable t) {
                        Toast.makeText(AddProductActivity.this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private ProductModel addProduct(){
        ProductModel productModel = new ProductModel(
                edtName.getText().toString().trim(),
                Integer.parseInt(edtIdCategory.getText().toString().trim()),
                edtDescription.getText().toString().trim(),
                Double.parseDouble(edtPrice.getText().toString().trim()),
                Integer.parseInt(edtQuantity.getText().toString().trim())
        );
        return productModel;
    }
    private void initComponents(){
        edtName = findViewById(R.id.edtNameProduct);
        edtDescription = findViewById(R.id.edtDesciption);
        edtIdCategory = findViewById(R.id.edtIdCategory);
        edtPrice = findViewById(R.id.edtPrice);
        edtQuantity = findViewById(R.id.edtQuantity);
        btnAdd = findViewById(R.id.btnAdd);
    }
    private void initData(){}
}