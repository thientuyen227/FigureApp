package com.example.figureapp.service;

import com.example.figureapp.model.User;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface IUserService {
    @FormUrlEncoded
    @POST("/users/login")
    Call<Response> logIn(@Field("username") String username, @Field("password") String password);
    @FormUrlEncoded
    @POST("/users/signup")
    Call<Response> signUp(@Field("email") String email, @Field("username") String username, @Field("password") String password);

    @GET("/users/getProfile")
    Call<User> getProfile(@Header("Authorization") String token);
    @PUT("/users/changeAvatar")
    @Multipart
    Call<User> changeAvatar(
            @Part MultipartBody.Part avatar,
            @Header("Authorization") String token);
    @FormUrlEncoded
    @PUT("/users/updateProfile")
    Call<ArrayList<User>> updateProfile(
            @Field("name") String name,
            @Field("email") String email,
            @Field("avatar") String avatar,
            @Field("idCard") String idCard,
            @Field("eWallet") int eWallet,
            @Header("Authorization") String token);
}
