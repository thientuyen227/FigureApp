package com.example.figureapp.service;

import com.example.figureapp.model.OrderModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IOrderService {
    @GET("/listorders")
    Call<ArrayList<OrderModel>> getAllOrderItem(@Header("Authorization") String token);
    @POST("/checkout")
    Call<ArrayList<OrderModel>> checkoutOrder(@Field("productId") int productId,@Field("count") int count, @Header("Authorization") String token);
}
