package com.example.homegarden.util

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private val client = OkHttpClient.Builder().build()
    var retrofitClient: Retrofit? = null
        get(){
        if(field==null){
            field = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(OPEN_WEATHER_MAP_API_BASE_URL)
                .client(client)
                .build()
        }
        return field
    }
}