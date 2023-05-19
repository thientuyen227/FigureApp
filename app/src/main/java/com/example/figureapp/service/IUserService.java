package com.example.figureapp.service;

import androidx.room.Delete;

import com.example.figureapp.model.User;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IUserService {
    @GET("/users/getAllUser")
    Call<ArrayList<User>> getAllUser(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("/users/deleteUser")
    Call<User> deleteUser(@Header("Authorization") String token, @Field("userId") int userId );
    @FormUrlEncoded
    @POST("/users/login")
    Call<Response> logIn(@Field("username") String username, @Field("password") String password);
    @FormUrlEncoded
    @POST("/users/signup")
    Call<Response> signUp(@Field("username") String username,@Field("email") String email,  @Field("password") String password, @Field("name") String name);

    @GET("/users/getProfile")
    Call<User> getProfile(@Header("Authorization") String token);
    @FormUrlEncoded
    @POST("/users/getUser")
    Call<User> getUser(@Field("userId") int userId);
    @FormUrlEncoded
    @PUT("/users/editUser")
    Call<User> updateUser(@Header("Authorization") String token,
                          @Field("name") String name,
                          @Field("password") String password,
                          @Field("role") String role,
                          @Field("email") String email,
                          @Field("idCard") String idCard,
                          @Field("eWallet") int eWallet,
                          @Field("userId") int userId);
    @PUT("/users/changeAvatar")
    @Multipart
    Call<User> changeAvatar(
            @Part MultipartBody.Part avatar,
            @Header("Authorization") String token);
    @FormUrlEncoded
    @PUT("/users/updateProfile")
    Call<User> updateProfile(
            @Field("name") String name,
            @Field("email") String email,
            @Field("eWallet") int eWallet,
            @Header("Authorization") String token);
}
