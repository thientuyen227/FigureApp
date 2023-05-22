package com.example.figureapp.adapter.admin;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.figureapp.MainActivity;
import com.example.figureapp.R;
import com.example.figureapp.SelectListener;
import com.example.figureapp.UpdateUserActivity;
import com.example.figureapp.adapter.UserAdapter;
import com.example.figureapp.model.CategoryModel;
import com.example.figureapp.model.User;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.ICategoryService;
import com.example.figureapp.service.IUserService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<CategoryModel> categoryModels;
    Context context;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";

    public CategoryAdapter(ArrayList<CategoryModel> categoryModels, Context context) {
        this.categoryModels = categoryModels;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String token =  sharedPreferences.getString(KEY_TOKEN, null);
        CategoryModel categoryModel = categoryModels.get(position);
        holder.edtNameCategory.setText(categoryModel.getName());
        holder.edtIdCategory.setText(String.valueOf(categoryModel.getId()));
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseAPIService.createService(ICategoryService.class).deleteCategory("Bearer "+ token
                        , categoryModel.getId()).enqueue(new Callback<CategoryModel>() {
                    @Override
                    public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                        context.startActivity(new Intent(context, MainActivity.class));
                        Toast.makeText(context, "Đã xóa category thành công", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<CategoryModel> call, Throwable t) {
                        Toast.makeText(context, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = holder.edtNameCategory.getText().toString().trim();
                if (categoryName.isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập Name Category", Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseAPIService.createService(ICategoryService.class).editCategory("Bearer " + token
                        , categoryModel.getId(),categoryName ).enqueue(new Callback<CategoryModel>() {
                    @Override
                    public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                        Toast.makeText(context, "Update Category thành công", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, MainActivity.class));
                    }

                    @Override
                    public void onFailure(Call<CategoryModel> call, Throwable t) {
                        Toast.makeText(context, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    public int getItemCount() {
        return categoryModels.size();
    }
    public static class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        EditText edtNameCategory;
        TextView edtIdCategory;
        Button btnUpdate, btnDelete, btnAdd;
        private SelectListener selectListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edtNameCategory = itemView.findViewById(R.id.edtNameCategory);
            edtIdCategory = itemView.findViewById(R.id.edtIdCategory);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete =  itemView.findViewById(R.id.btnDelete);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(SelectListener selectListener)
        {
            this.selectListener = selectListener;
        }
        @Override
        public void onClick(View view) {
            selectListener.onItemClicked(view,getAdapterPosition(),false);
        }
    }
}
