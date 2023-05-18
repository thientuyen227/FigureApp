package com.example.figureapp.service;

import com.example.figureapp.model.CategoryModel;
import com.example.figureapp.model.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ICategoryService {
    @GET("/category/listcategories")
    Call<ArrayList<CategoryModel>> getAllCategories();
    @FormUrlEncoded
    @POST("/category/products")
    Call<ArrayList<ProductModel>> getCategoryItem(@Field("idCategory") int id);
    @FormUrlEncoded
    @POST("/category/addCategory")
    Call<CategoryModel> addCategory(@Header("Authorization") String token, @Field("categoryName") String categoryName);
    @FormUrlEncoded
    @PUT("/category/editCategory")
    Call<CategoryModel> editCategory(@Header("Authorization") String token,
            @Field("id") int idCategory
            , @Field("name") String categoryName);
    @GET("/category/getAllCategory")
    Call<ArrayList<CategoryModel>> getAll();
     @FormUrlEncoded
    @POST("/category/deleteCategory")
    Call<CategoryModel> deleteCategory(@Header("Authorization") String token, @Field("idCategory") int idCategory);
}
