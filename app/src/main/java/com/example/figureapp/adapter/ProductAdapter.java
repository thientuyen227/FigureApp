package com.example.figureapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.figureapp.DetailProductActivity;
import com.example.figureapp.R;
import com.example.figureapp.SelectListener;
import com.example.figureapp.database.ProductDatabase;
import com.example.figureapp.entities.Products;
import com.example.figureapp.model.ProductModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
{
    ArrayList<ProductModel> products;

    public iClickListener listener;
    Context context;
    private boolean isFollowing = false;


    public ProductAdapter(ArrayList<ProductModel> products, Context context, iClickListener listener) {
        this.products = products;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.followingproduct, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel product = products.get(position);
        Glide.with(context).load(product.getImageProduct()).into(holder.ProductImage);
        holder.ProductTitle.setText(product.getName());

        holder.setItemClickListener(new SelectListener() {
            @Override
            public void onItemClicked(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("id", product.getId());
                context.startActivity(intent);
            }
        });
        holder.imFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFollowing = !isFollowing;

                Products products1 = new Products(0,product.getName(), product.getImageProduct(), product.getDescription());
                if(isCheckExist(products1)){
                    Toast.makeText(context, "Bạn đã theo dõi" + products1.getName() + " rồi", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isFollowing) {
                    ProductDatabase.getInstance(context).followingProductDao().insertFollowingProduct(products1);
                    Toast.makeText(context,"Bạn đã theo dõi "+ products1.getName(), Toast.LENGTH_SHORT).show();
                    holder.imFollow.setImageResource(R.drawable.ic_follow_red);
                } else {
                        ProductDatabase.getInstance(context).followingProductDao().deleteFollowingProduct(products1);
                        Toast.makeText(context,"Bạn đã ngưng theo dõi "+ products1.getName(), Toast.LENGTH_SHORT).show();
                    holder.imFollow.setImageResource(R.drawable.ic_follow);
                }

            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateProducts(ArrayList<ProductModel> products){
        this.products = products;
        notifyDataSetChanged();
    }
    private boolean isCheckExist(@NotNull Products products) {
        List<Products> list = ProductDatabase.getInstance(context).followingProductDao().checkUser(products.getName());
        return list != null && !list.isEmpty();
    }
    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ProductImage;
        TextView ProductTitle;
        ImageButton imFollow;
        private SelectListener selectListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductImage = itemView.findViewById(R.id.product_image);
            ProductTitle = itemView.findViewById(R.id.product_name);
            imFollow = itemView.findViewById(R.id.im_follow);
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
    public interface iClickListener {
        void addProduct(Products product);
    }
}
