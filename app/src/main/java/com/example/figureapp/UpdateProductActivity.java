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
import com.example.figureapp.model.User;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.IProductService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProductActivity extends AppCompatActivity {
    EditText edtName, edtIdCategory, edtPrice, edtDescription, edtQuantity;
    Button btnUpdate;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        initComponents();
        initData();;
        loadProduct();
    }
    private void loadProduct(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String token =  sharedPreferences.getString(KEY_TOKEN, null);
        BaseAPIService.createService(IProductService.class).getProduct().enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                ProductModel productModel = response.body();
                setProduct(productModel);
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    ProductModel productModel = updateProduct();
                    @Override
                    public void onClick(View v) {
                        BaseAPIService.createService(IProductService.class).updateProduct("Bearer" + token, productModel.getName(), productModel.getIdCategory(),
                                productModel.getDescription(), productModel.getPrice(),productModel.getQuantity(),productModel.getId()).enqueue(new Callback<ProductModel>() {
                            @Override
                            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                                Toast.makeText(UpdateProductActivity.this, "Update product thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UpdateProductActivity.this, MainActivity.class));
                            }

                            @Override
                            public void onFailure(Call<ProductModel> call, Throwable t) {
                                Toast.makeText(UpdateProductActivity.this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {

            }
        });
    }
    private ProductModel updateProduct(){
        ProductModel productModel = new ProductModel(
                edtName.getText().toString().trim(),
                Integer.parseInt(edtIdCategory.getText().toString().trim()),
                edtDescription.getText().toString().trim(),
                Double.parseDouble(edtPrice.getText().toString().trim()),
                Integer.parseInt(edtQuantity.getText().toString().trim())
        );
        return productModel;
    }
    private void setProduct(ProductModel products){
        edtName.setText(products.getName());
        edtDescription.setText(products.getDescription());
        edtIdCategory.setText(products.getIdCategory());
        edtPrice.setText(String.valueOf(products.getPrice()));
        edtQuantity.setText(products.getQuantity());
    }
    private void initComponents(){
        edtName = findViewById(R.id.edtNameProduct);
        edtDescription = findViewById(R.id.edtDesciption);
        edtIdCategory = findViewById(R.id.edtIdCategory);
        edtPrice = findViewById(R.id.edtPrice);
        edtQuantity = findViewById(R.id.edtQuantity);
        btnUpdate = findViewById(R.id.btnUpdate);
    }
    private void initData(){}

}