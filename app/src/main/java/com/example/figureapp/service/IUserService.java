package com.example.figureapp.service;

import com.example.figureapp.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IUserService {
    @FormUrlEncoded
    @POST("/users/login")
    Call<Response> logIn(@Field("username") String username, @Field("password") String password);
    @FormUrlEncoded
    @POST("/users/signup")
    Call<Response> signUp(@Field("email") String email, @Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("/users/profile")
    Call<ArrayList<User>> profile(@Field("iduser") int id);
}
