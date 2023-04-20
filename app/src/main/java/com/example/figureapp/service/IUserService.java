package com.example.figureapp.service;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IUserService {
    @FormUrlEncoded
    @POST("/shoppingapp/registrationapi.php?apicall=login")
    Call<Response> logIn(@Field("username") String username, @Field("password") String password);
    @FormUrlEncoded
    @POST("/shoppingapp/registrationapi.php?apicall=signup")
    Call<Response> signUp(@Field("email") String email, @Field("username") String username, @Field("password") String password);
}