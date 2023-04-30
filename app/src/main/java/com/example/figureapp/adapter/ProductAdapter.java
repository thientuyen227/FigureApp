package com.example.figureapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.figureapp.R;
import com.example.figureapp.SelectListener;
import com.example.figureapp.model.ProductModel;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
{
    ArrayList<ProductModel> products;
    Context context;

    public ProductAdapter(ArrayList<ProductModel> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listproduct, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel product = products.get(position);
        Glide.with(context).load(product.getImageProduct()).into(holder.ProductImage);
        holder.ProductTitle.setText(product.getName());
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateProducts(ArrayList<ProductModel> products){
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ProductImage;
        TextView ProductTitle;
        private SelectListener selectListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductImage = itemView.findViewById(R.id.product_image);
            ProductTitle = itemView.findViewById(R.id.product_name);
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
