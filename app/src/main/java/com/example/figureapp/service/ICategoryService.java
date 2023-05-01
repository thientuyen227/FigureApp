package com.example.figureapp.service;

import com.example.figureapp.model.CategoryModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ICategoryService {
    @GET("/category/listcategory")
    Call<ArrayList<CategoryModel>> getAllCategories();
}