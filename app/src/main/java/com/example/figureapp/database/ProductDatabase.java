package com.example.figureapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.figureapp.dao.FollowingProductDao;
import com.example.figureapp.entities.Products;

@Database(entities = {Products.class}, version = 1)
public abstract class ProductDatabase extends RoomDatabase {
    private static final String DATABASE_NAME= "product.db";
    private static ProductDatabase instance;
    public static synchronized ProductDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ProductDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract FollowingProductDao followingProductDao();
}
