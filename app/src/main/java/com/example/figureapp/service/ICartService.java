package com.example.figureapp.service;

import com.example.figureapp.model.CartModel;
import com.example.figureapp.model.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface ICartService {
    @GET("/cart")
    Call<ArrayList<ProductModel>> getAllProductInCart(@Field("iduser")int id);
}
