package com.example.figureapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.figureapp.dao.CartDao;
import com.example.figureapp.dao.FollowingProductDao;
import com.example.figureapp.entities.Cart;
import com.example.figureapp.entities.Products;

@Database(entities = {Cart.class}, version = 1, exportSchema = true)
public abstract class CartDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "cart.db";
    private static CartDatabase instance;

    public static synchronized CartDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            CartDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
        public abstract CartDao cartDao();
}
