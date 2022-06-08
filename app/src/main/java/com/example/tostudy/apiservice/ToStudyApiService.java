package com.example.tostudy.apiservice;

import com.example.tostudy.apiservice.dto.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ToStudyApiService {

    @FormUrlEncoded
    @POST("toStudy.php?controller=users&req=userInfo")
    Call<UserResponse> getUserInfo(
            @Field("email") String email
    );

}
