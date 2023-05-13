package com.example.figureapp.service;

import com.example.figureapp.entities.Cart;
import com.example.figureapp.model.CartModel;
import com.example.figureapp.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ICartService {
    @POST("/cart/checkout")
    Call<List<Cart>> checkOutCart(List<Integer> productId,@Header("Authorization") String token);
}
