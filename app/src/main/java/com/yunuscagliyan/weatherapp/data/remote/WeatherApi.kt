package com.yunuscagliyan.weatherapp.data.remote

import com.yunuscagliyan.weatherapp.data.remote.model.WeatherResponse
import com.yunuscagliyan.weatherapp.data.remote.url.WeatherUrl
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(WeatherUrl.GET_ONE_CALL)
    suspend fun getOneCall(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude:String,
        @Query("units") units:String,
        @Query("lang") lang:String,
        @Query("appid") apiKey:String,
    ): WeatherResponse
}