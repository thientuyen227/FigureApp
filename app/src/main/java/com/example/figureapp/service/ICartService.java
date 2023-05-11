package com.example.figureapp.service;

import com.example.figureapp.model.CartModel;
import com.example.figureapp.model.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ICartService {
    @GET("/cart/itemcarts")
    Call<ArrayList<ProductModel>> getAllProductInCart(@Header("Authorization") String token);
}
