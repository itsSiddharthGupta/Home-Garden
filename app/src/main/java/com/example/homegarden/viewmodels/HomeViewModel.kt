package com.example.homegarden.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homegarden.dataclasses.WeatherToday
import com.example.homegarden.util.ApiService
import com.example.homegarden.util.OPEN_WEATHER_MAP_API_KEY
import com.example.homegarden.util.OPEN_WEATHER_MAP_API_QUERY_UNIT
import com.example.homegarden.util.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val city: String) : ViewModel() {
    var weatherLiveData: MutableLiveData<WeatherToday> = MutableLiveData()

    init {
        getTodayWeather()
    }

    private fun getTodayWeather() {
        Retrofit.retrofitClientWeather?.create(ApiService::class.java)
            ?.getTodayWeather(city, OPEN_WEATHER_MAP_API_QUERY_UNIT, OPEN_WEATHER_MAP_API_KEY)
            ?.enqueue(object : Callback<WeatherToday> {
                override fun onResponse(
                    call: Call<WeatherToday>,
                    response: Response<WeatherToday>
                ) {
                    if(response.isSuccessful && response.body()!=null){
                        if(response.body()!!.cod == 200) {
                            Log.e("Weather", response.body().toString())
                            weatherLiveData.value = response.body()
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherToday>, t: Throwable) {
                    Log.e("ErrorWeatherFetch", t.toString())
                }
            })
    }
}