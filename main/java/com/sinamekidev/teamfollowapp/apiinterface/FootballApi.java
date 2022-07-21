package com.sinamekidev.teamfollowapp.apiinterface;

import com.sinamekidev.teamfollowapp.models.QueryModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FootballApi {
   // @GET("search.json?q={league_name}+teams&api_key=30bcb7e2d495b6bae20de36ba97330758c433b04d318b38aa358efc5f366b5ce")
    @GET("search.json")
    Call<QueryModel> getQueryModel(@Query("q") String league_name,@Query("api_key") String api_key);
}
