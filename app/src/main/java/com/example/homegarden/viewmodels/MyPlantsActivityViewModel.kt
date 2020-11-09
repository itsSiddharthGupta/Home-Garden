package com.example.homegarden.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homegarden.dataclasses.PlantDetails
import com.example.homegarden.dataclasses.PlantsListResponse
import com.example.homegarden.util.ApiService
import com.example.homegarden.util.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPlantsActivityViewModel : ViewModel() {
    var plants: MutableLiveData<List<PlantDetails>> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getIndoorPlants() {
        isLoading.value = true
        Retrofit.retrofitClientPlant!!.create(ApiService::class.java).getIndoorPlants()
            .enqueue(object : Callback<PlantsListResponse> {
                override fun onResponse(
                    call: Call<PlantsListResponse>,
                    response: Response<PlantsListResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.d("ResponseIndoorPlant", response.body().toString())
                        val status = response.body()!!.status
                        if (status == "success") {
                            plants.value = response.body()!!.obj
                        }
                        isLoading.value = false
                    }
                }

                override fun onFailure(call: Call<PlantsListResponse>, t: Throwable) {
                    Log.d("ErrorPlant", t.toString())
                    isLoading.value = false
                }
            })
    }

    fun getOutdoorPlants() {
        isLoading.value = true
        Retrofit.retrofitClientPlant!!.create(ApiService::class.java).getOutdoorPlants()
            .enqueue(object : Callback<PlantsListResponse> {
                override fun onResponse(
                    call: Call<PlantsListResponse>,
                    response: Response<PlantsListResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.d("ResponseIndoorPlant", response.body().toString())
                        val status = response.body()!!.status
                        if (status == "success") {
                            plants.value = response.body()!!.obj
                        }
                        isLoading.value = false
                    }
                }

                override fun onFailure(call: Call<PlantsListResponse>, t: Throwable) {
                    Log.d("ErrorPlant", t.toString())
                    isLoading.value = false
                }
            })
    }
}