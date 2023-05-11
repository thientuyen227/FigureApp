package com.example.figureapp.service;

import com.example.figureapp.model.FollowingProductModel;
import com.example.figureapp.model.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IFollowingProductService {
    @GET("/follow/followingproducts")
    Call<ArrayList<ProductModel>> getAllFollowingProducts(@Header("Authorization") String token);
}
