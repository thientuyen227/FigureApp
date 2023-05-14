package com.example.figureapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.figureapp.entities.Products;

import java.util.List;


@Dao
public interface FollowingProductDao {
    @Query("SELECT * FROM product")
    List<Products> getAll();
    @Query("SELECT * FROM product where name= :name")
    List<Products> checkUser(String name);
    @Insert
    void insertFollowingProduct(Products... products);
    @Delete
    void deleteFollowingProduct(Products... products);
}
