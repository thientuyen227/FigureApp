package com.example.figureapp.service;

import com.example.figureapp.model.FollowingProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IFollowingProductService {
    @GET("/appfoods/lastproduct.php")
    Call<ArrayList<FollowingProductModel>> getAllFollowingProducts();
}
