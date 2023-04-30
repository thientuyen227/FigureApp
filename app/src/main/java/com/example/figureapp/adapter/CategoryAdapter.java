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
import com.example.figureapp.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<CategoryModel> categories;
    Context context;
    private OnItemClickListener listener;
    public CategoryAdapter(List<CategoryModel> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listcategory, parent, false);
        return new ViewHolder(view);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel category = categories.get(position);
        Glide.with(context).load(category.getImages()).into(holder.categoryImage);
        holder.categoryTitle.setText(category.getName());
//        holder.categoryImage.setImageBitmap(bitmap);
//        Glide.with(context).load(category.getImages()).into(holder.categoryImage);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateCategories(ArrayList<CategoryModel> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView categoryImage;
        TextView categoryTitle;
        private SelectListener selectListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.category_image);
            categoryTitle = itemView.findViewById(R.id.category_name);
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
