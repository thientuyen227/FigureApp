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
import com.example.figureapp.model.OrderModel;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<OrderModel> orderModels;
    private Context context;

    public OrderAdapter(ArrayList<OrderModel> orderModels, Context context) {
        this.orderModels = orderModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_order, parent, false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel orders = orderModels.get(position);
        Glide.with(context).load(orders.getOrderitems().get(position).getProduct().getImageProduct()).into(holder.orderImage);
        holder.orderTitle.setText(orders.getOrderitems().get(position).getProduct().getName());
    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView orderImage;
        TextView orderTitle;
        private SelectListener selectListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderImage = itemView.findViewById(R.id.im_product);
            orderTitle = itemView.findViewById(R.id.tv_product_name);
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
