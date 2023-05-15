package com.example.figureapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.figureapp.CartActivity;
import com.example.figureapp.DetailProductActivity;
import com.example.figureapp.HomeActivity;
import com.example.figureapp.R;
import com.example.figureapp.SelectListener;
import com.example.figureapp.database.CartDatabase;
import com.example.figureapp.database.ProductDatabase;
import com.example.figureapp.entities.Cart;
import com.example.figureapp.model.CartModel;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<Cart> cartModel;
    private boolean isCart=false;

    public CartAdapter(List<Cart> cartModel, Context context) {
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
        Cart carts = cartModel.get(position);
        Glide.with(context).load(carts.getImage()).into(holder.cartImage);
        holder.cartTitle.setText(carts.getName());
        holder.cartPrice.setText(String.valueOf(carts.getPrice()));

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentNumber = Integer.parseInt(holder.tvCount.getText().toString());
                int newNumber = currentNumber + 1;
                holder.tvCount.setText(String.valueOf(newNumber));
//                String count = holder.tvCout.getText().toString().trim();
//
//                carts.setQuantity(Integer.parseInt(count));
//                System.out.println(count);
//                CartDatabase.getInstance(context).cartDao().updateProductInCart(carts);
//                System.out.println(count);
                notifyDataSetChanged();
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Giảm số lượng sản phẩm xuống 1 đơn vị
                int currentNumber = Integer.parseInt(holder.tvCount.getText().toString());
                int newNumber = currentNumber - 1;
                holder.tvCount.setText(String.valueOf(newNumber));
                // Nếu số lượng sản phẩm bằng 0 thì xóa đối tượng Cart khỏi giỏ hàng
                if (newNumber == 0) {
                    CartDatabase.getInstance(context).cartDao().deleteProductInCart(carts);
                    Toast.makeText(context, "Sản phẩm đã được xóa khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        holder.setItemClickListener(new SelectListener() {
            @Override
            public void onItemClicked(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("id", carts.getId());
                intent.putExtra("count", holder.tvCount.getText().toString());
                context.startActivity(intent);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCart = !isCart;
                CartDatabase.getInstance(context).cartDao().deleteProductInCart(carts);
                Toast.makeText(context,"Bạn đã xóa "+ carts.getName() +" khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return cartModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView cartImage;
        TextView cartTitle, cartPrice, tvCount;
        ImageButton btnDelete;
        ImageView btnAdd, btnMinus;

        private SelectListener selectListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cartImage = itemView.findViewById(R.id.product_image);
            this.cartTitle = itemView.findViewById(R.id.product_name);
            this.btnDelete = itemView.findViewById(R.id.btn_delete);
            this.btnAdd = itemView.findViewById(R.id.btnAdd);
            this.cartPrice = itemView.findViewById(R.id.tv_price);
            this.btnMinus = itemView.findViewById(R.id.btnMinus);
            this.tvCount = itemView.findViewById(R.id.tv_count);
        }
        public void setItemClickListener(SelectListener selectListener)
        {
            this.selectListener = selectListener;
        }
        @Override
        public void onClick(View v) {
            selectListener.onItemClicked(v , getAdapterPosition(), false);
        }
    }
}
