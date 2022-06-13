package com.example.tostudy.apiservice;

import com.example.tostudy.apiservice.dto.BooleanResponse;
import com.example.tostudy.apiservice.dto.EventoResponse;
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

    @FormUrlEncoded
    @POST("toStudy.php?controller=users&req=updateUser")
    Call<UserResponse> editUser(
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

    @FormUrlEncoded
    @POST("toStudy.php?controller=events&req=allEvents")
    Call<EventoResponse> getEvents(
            @Query("id") String data,
            @Field("date") String date,
            @Query("mode") String mode
    );

    @FormUrlEncoded
    @POST("toStudy.php?controller=events&req=insertEvents")
    Call<BooleanResponse> saveEvents(
            @Field("data") String data
    );

    @GET("toStudy.php?controller=events&req=deleteEvents")
    Call<BooleanResponse> deleteEvents(
            @Query("id") String data
    );

    @FormUrlEncoded
    @POST("toStudy.php?controller=events&req=updateEvents")
    Call<BooleanResponse> editEvents(
            @Field("data") String data
    );
}
