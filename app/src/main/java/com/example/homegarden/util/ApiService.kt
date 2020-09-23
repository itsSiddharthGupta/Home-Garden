package com.example.homegarden.util

import com.example.homegarden.dataclasses.WeatherToday
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather/")
    fun getTodayWeather(
        @Query("q") cityName: String,
        @Query("units") unit: String,
        @Query("appid") id: String
    ) : Call<WeatherToday>
}