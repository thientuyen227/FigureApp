package com.example.figureapp.service;

import com.example.figureapp.entities.Cart;
import com.example.figureapp.model.OrderModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IOrderService {
    @GET("/order/listorders")
    Call<ArrayList<OrderModel>> getAllOrderItem(@Header("Authorization") String token);
    @FormUrlEncoded
    @POST("/order/checkout")
    Call<Cart> checkoutOrder(@Field("idProduct") List<Integer> productId, @Field("count") List<Integer> count, @Header("Authorization") String token);
}
