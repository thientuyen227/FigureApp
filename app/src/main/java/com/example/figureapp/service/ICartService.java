package com.example.figureapp.service;

import com.example.figureapp.model.CartModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ICartService {
    @GET("/cart")
    Call<ArrayList<CartModel>> getAllProductInCart();
}
