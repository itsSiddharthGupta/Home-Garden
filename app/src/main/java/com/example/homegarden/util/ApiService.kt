package com.example.homegarden.util

import com.example.homegarden.dataclasses.PlantsListResponse
import com.example.homegarden.dataclasses.WeatherToday
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather/")
    fun getTodayWeather(
        @Query("q") cityName: String,
        @Query("units") unit: String,
        @Query("appid") id: String
    ): Call<WeatherToday>

    @GET("plants/")
    fun getPlantByName(@Query("name") name: String): Call<JsonObject>

    @GET("plants/indoor")
    fun getIndoorPlants(): Call<PlantsListResponse>

    @GET("plants/outdoor")
    fun getOutdoorPlants(): Call<PlantsListResponse>
}