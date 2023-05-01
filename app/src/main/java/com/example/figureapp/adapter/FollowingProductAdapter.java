package com.example.figureapp.adapter;

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
import com.example.figureapp.model.FollowingProductModel;

import java.util.ArrayList;
import java.util.List;

public class FollowingProductAdapter extends RecyclerView.Adapter<FollowingProductAdapter.ViewHolder> {

    ArrayList<FollowingProductModel> followingProducts;
    Context context;

    public FollowingProductAdapter(ArrayList<FollowingProductModel> followingProduct, Context context) {
        this.followingProducts = followingProduct;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.followingproduct, parent, false);
        return new FollowingProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FollowingProductModel followingProduct = followingProducts.get(position);
        Glide.with(context).load(followingProduct.getProduct().getImageProduct()).into(holder.productImage);
        holder.productTitle.setText(followingProduct.getProduct().getName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public static class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImage;
        TextView productTitle;
        private SelectListener selectListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_name);
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
