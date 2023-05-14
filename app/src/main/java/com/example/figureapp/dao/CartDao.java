package com.example.figureapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.figureapp.entities.Cart;
import com.example.figureapp.entities.Products;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart")
    List<Cart> getAll();

    @Query("SELECT Cart.idProduct FROM Cart")
    List<Integer> getAllIdProduct();
    @Query("SELECT * FROM cart where name= :name")
    List<Cart> checkCart(String name);
    @Insert
    void insertProductInCart(Cart... cart);
    @Delete
    void deleteProductInCart(Cart... cart);
    @Update
    void updateProductInCart(Cart... cart);
}
