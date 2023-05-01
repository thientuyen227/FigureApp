package com.example.figureapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.figureapp.R;
import com.example.figureapp.SelectListener;
import com.example.figureapp.model.CartModel;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CartModel> cartModel;

    public CartAdapter(Context context, ArrayList<CartModel>cartModel) {
        this.context = context;
        this.cartModel = cartModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartModel carts = cartModel.get(position);
        Glide.with(context).load(carts.getCartItemModels().get(position).getProductModel().getImageProduct()).into(holder.cartImage);
        holder.cartTitle.setText(carts.getCartItemModels().get(position).getProductModel().getName());
    }

    @Override
    public int getItemCount() {
        return cartModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView cartImage;
        TextView cartTitle;
        private SelectListener selectListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cartImage = itemView.findViewById(R.id.im_product);
            this.cartTitle = itemView.findViewById(R.id.product_name);
        }

        @Override
        public void onClick(View v) {
            selectListener.onItemClicked(v , getAdapterPosition(), false);
        }
    }
}
