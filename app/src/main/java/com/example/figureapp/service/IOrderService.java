package com.example.figureapp.service;

import com.example.figureapp.model.OrderModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IOrderService {
    @GET("/order")
    Call<ArrayList<OrderModel>> getAllOrderItem();
}
