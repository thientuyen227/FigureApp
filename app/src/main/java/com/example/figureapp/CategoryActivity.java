package com.example.figureapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.figureapp.adapter.admin.CategoryAdapter;
import com.example.figureapp.model.CategoryModel;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.ICategoryService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends BaseActivity {
    RecyclerView rvCategory;
    EditText tvNameCategory;
    Button btnAdd;
    ArrayList<CategoryModel> categorys;
    com.example.figureapp.adapter.admin.CategoryAdapter categoryAdapter;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setUpActionBar("Category");
        initComponents();
        initData();
        loadCategory();
    }
    private void loadCategory(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String token =  sharedPreferences.getString(KEY_TOKEN, null);
        BaseAPIService.createService(ICategoryService.class).getAll().enqueue(new Callback<ArrayList<CategoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {
                categorys = response.body();
                categoryAdapter = new CategoryAdapter(categorys,CategoryActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CategoryActivity.this, LinearLayoutManager.VERTICAL, false);
                rvCategory.setLayoutManager(linearLayoutManager);
                rvCategory.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String categoryName = tvNameCategory.getText().toString().trim();

                        BaseAPIService.createService(ICategoryService.class).addCategory("Bearer " + token, categoryName).enqueue(new Callback<CategoryModel>() {
                            @Override
                            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                                loadCategory();
                                Toast.makeText(CategoryActivity.this, "Update user thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CategoryActivity.this, MainActivity.class));
                            }

                            @Override
                            public void onFailure(Call<CategoryModel> call, Throwable t) {
                                Toast.makeText(CategoryActivity.this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable t) {

            }
        });
    }
    private void initComponents(){
        rvCategory = findViewById(R.id.rc_category);
        tvNameCategory = findViewById(R.id.tvNameCategory);
        btnAdd = findViewById(R.id.btnAdd);
    }
    private void initData(){
        categorys = new ArrayList<>();
    }
}