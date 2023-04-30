package com.example.figureapp.service;

import com.example.figureapp.model.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IProductService {
    @GET("/appfoods/lastproduct.php")
    Call<ArrayList<ProductModel>> getAllProducts();

    @FormUrlEncoded
    @POST("/appfoods/getcategory.php")
    Call<ArrayList<ProductModel>> getCategoryItem(@Field("idcategory") int id);
}
