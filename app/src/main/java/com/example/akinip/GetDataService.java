package com.example.akinip;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetDataService {
    @GET("alldata")
    public Call<DataEntity> allData();

    @POST("link")
    public Call<LinkSucces> auth(@Body LinkSucces linkSucces );
}
