package com.example.tostudy.apiservice;

import com.example.tostudy.apiservice.dto.BooleanResponse;
import com.example.tostudy.apiservice.dto.ObjetivoResponse;
import com.example.tostudy.apiservice.dto.UserResponse;
import com.example.tostudy.data.model.Objetivo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ToStudyApiService {

    @FormUrlEncoded
    @POST("toStudy.php?controller=users&req=userInfo")
    Call<UserResponse> getUserInfo(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("toStudy.php?controller=users&req=insertUser")
    Call<BooleanResponse> saveUser(
            @Field("data") String data
    );

    @GET("toStudy.php?controller=objectives&req=allObjectives")
    Call<ObjetivoResponse> getObjetives(
            @Query("id") String data
    );

    @FormUrlEncoded
    @POST("toStudy.php?controller=objectives&req=insertObjectives")
    Call<BooleanResponse> saveObjetive(
            @Field("data") String data
    );

    @GET("toStudy.php?controller=objectives&req=deleteObjectives")
    Call<BooleanResponse> deleteObjetive(
            @Query("id") String data
    );

    @FormUrlEncoded
    @POST("toStudy.php?controller=objectives&req=updateObjectives")
    Call<BooleanResponse> editObjetive(
            @Field("data") String data
    );

    @GET("toStudy.php?controller=objectives&req=allObjectives")
    Call<ObjetivoResponse> getEvens(
            @Query("id") String data
    );

    @FormUrlEncoded
    @POST("toStudy.php?controller=objectives&req=insertObjectives")
    Call<BooleanResponse> saveEvens(
            @Field("data") String data
    );

    @GET("toStudy.php?controller=objectives&req=deleteObjectives")
    Call<BooleanResponse> deleteEvense(
            @Query("id") String data
    );

    @FormUrlEncoded
    @POST("toStudy.php?controller=objectives&req=updateObjectives")
    Call<BooleanResponse> editEvens(
            @Field("data") String data
    );
}
