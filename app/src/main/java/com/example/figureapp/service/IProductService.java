package com.example.figureapp.service;

import com.example.figureapp.model.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface IProductService {
    @GET("/product/listproducts")
    Call<ArrayList<ProductModel>> getAllProducts();
    @FormUrlEncoded
    @POST("/product/detailproduct")
    Call<ArrayList<ProductModel>> getDetailProduct(@Field("id") int id);
    @GET("/product/searchProduct")
    Call<ArrayList<ProductModel>> searchProduct(@Query("keyWord") String query);
    @FormUrlEncoded
    @POST("/product/deleteProduct")
    Call<ProductModel> deleteProduct( @Header("Authorization") String token,@Field("productId") int productId);
    @GET("/product/getProduct")
    Call<ProductModel> getProduct();
    @FormUrlEncoded
    @PUT("/product/editProduct")
    Call<ProductModel> updateProduct(@Header("Authorization") String token,
                                     @Field("productName") String productName,
                                     @Field("idCategory") int idCategory,
                                     @Field("description") String description,
                                     @Field("price") double price,
                                     @Field("quantity") int quantity,
                                     @Field("productId") int productId);
    @FormUrlEncoded
    @POST("/product/addProduct")
    Call<ProductModel> addProduct(@Header("Authorization") String token,
                                     @Field("productName") String productName,
                                     @Field("idCategory") int idCategory,
                                     @Field("description") String description,
                                     @Field("price") double price,
                                     @Field("quantity") int quantity);

}
