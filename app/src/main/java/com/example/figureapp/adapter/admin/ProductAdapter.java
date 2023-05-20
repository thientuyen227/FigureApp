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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.figureapp.MainActivity;
import com.example.figureapp.ProductActivity;
import com.example.figureapp.R;
import com.example.figureapp.SelectListener;
import com.example.figureapp.UpdateProductActivity;
import com.example.figureapp.UpdateUserActivity;
import com.example.figureapp.model.ProductModel;
import com.example.figureapp.service.BaseAPIService;
import com.example.figureapp.service.IProductService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    ArrayList<ProductModel> productModels;

    public ProductAdapter(ArrayList<ProductModel> productModels, Context context) {
        this.productModels = productModels;
        this.context = context;
    }

    Context context;
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_TOKEN = "keytoken";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String token = sharedPreferences.getString(KEY_TOKEN, null);
        ProductModel productModel1 = productModels.get(position);
        holder.edtNameProduct.setText(productModel1.getName());
        holder.edtIdCategory.setText(String.valueOf(productModel1.getIdCategory()));
        holder.edtPrice.setText(String.valueOf(productModel1.getPrice()));
        holder.edtQuantity.setText(String.valueOf(productModel1.getQuantity()));
        holder.edtDescription.setText(productModel1.getDescription());
        Glide.with(context).load(productModel1.getImageProduct()).into(holder.imProduct);
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ProductModel productModel = updateProduct(holder, productModel1.getId());
                BaseAPIService.createService(IProductService.class).updateProduct("Bearer " + token, productModel.getName(), productModel.getIdCategory(),
                        productModel.getDescription(), productModel.getPrice(), productModel.getQuantity(), productModel.getId()).enqueue(new Callback<ProductModel>() {
                    @Override
                    public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                        System.out.println(productModel.getId());
                        Toast.makeText(context, "Update product thành công", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, MainActivity.class));
                    }

                    @Override
                    public void onFailure(Call<ProductModel> call, Throwable t) {
                        Toast.makeText(context, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseAPIService.createService(IProductService.class).deleteProduct("Bearer " +token, productModel1.getId()).enqueue(new Callback<ProductModel>() {
                    @Override
                    public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                        context.startActivity(new Intent(context, MainActivity.class));
                        Toast.makeText(context, "Đã xóa product thành công", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ProductModel> call, Throwable t) {
                        Toast.makeText(context, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private ProductModel updateProduct(ProductAdapter.ViewHolder holder, int productId){
        ProductModel productModel = new ProductModel(
                holder.edtNameProduct.getText().toString().trim(),
                Integer.parseInt(holder.edtIdCategory.getText().toString().trim()),
                holder.edtDescription.getText().toString().trim(),
                Double.parseDouble(holder.edtPrice.getText().toString().trim()),
                Integer.parseInt(holder.edtQuantity.getText().toString().trim())
        );
        productModel.setId(productId);
        return productModel;
    }
    @Override
    public int getItemCount() {
        return productModels.size();
    }
    public static class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView edtIdCategory, edtNameProduct, edtPrice, edtDescription, edtQuantity;
        ImageView imProduct;
        Button btnUpdate, btnDelete;
        private SelectListener selectListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edtIdCategory = itemView.findViewById(R.id.edtIdCategory);
            edtDescription = itemView.findViewById(R.id.edtDesciption);
            edtNameProduct = itemView.findViewById(R.id.edtNameProduct);
            edtPrice = itemView.findViewById(R.id.edtPrice);
            edtQuantity = itemView.findViewById(R.id.edtQuantity);
            imProduct = itemView.findViewById(R.id.imProduct);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete =  itemView.findViewById(R.id.btnDelete);
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
