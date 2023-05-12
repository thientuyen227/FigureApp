package com.example.figureapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
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
import com.example.figureapp.model.FollowingProductModel;

import java.util.ArrayList;
import java.util.List;

public class FollowingProductAdapter extends RecyclerView.Adapter<FollowingProductAdapter.ViewHolder> {

    List<Products> followingProducts;

    Context context;
    private boolean isFollowing = false;

    public FollowingProductAdapter(List<Products> followingProduct, Context context) {
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
        Products followingProduct = followingProducts.get(position);
        Glide.with(context).load(followingProduct.getImage()).into(holder.productImage);
        holder.productTitle.setText(followingProduct.getName());
        holder.setItemClickListener(new SelectListener() {
            @Override
            public void onItemClicked(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("id", followingProduct.getId());
                context.startActivity(intent);
            }
        });
        holder.imFollow.setImageResource(R.drawable.ic_follow_red);
        holder.imFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFollowing = !isFollowing;
                    ProductDatabase.getInstance(context).followingProductDao().deleteFollowingProduct(followingProduct);
                        Toast.makeText(context,"Bạn đã ngưng theo dõi "+ followingProduct.getName(), Toast.LENGTH_SHORT).show();
                    holder.imFollow.setImageResource(R.drawable.ic_follow);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return followingProducts.size();
    }
    public static class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImage;
        TextView productTitle;
        private SelectListener selectListener;
        ImageButton imFollow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_name);
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
}
