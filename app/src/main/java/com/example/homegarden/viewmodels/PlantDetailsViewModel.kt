package com.example.homegarden.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homegarden.dataclasses.PlantDetails
import com.example.homegarden.util.ApiService
import com.example.homegarden.util.Retrofit
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlantDetailsViewModel(name: String) : ViewModel() {
    val plantDetails: MutableLiveData<PlantDetails> = MutableLiveData()

    init {
        getPlantDetails(name)
    }

    fun getPlantDetails(name: String) {
        Retrofit.retrofitClientPlant?.create(ApiService::class.java)?.getPlantByName(name)
            ?.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful && response.body() != null) {
                        Log.d("ResponsePlant", response.body().toString())
                        val status = response.body()!!.get("status").asString
                        if (status == "success") {
                            val json = response.body()!!.get("obj").asJsonObject.toString()
                            plantDetails.value = Gson().fromJson(json, PlantDetails::class.java)
                        }
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("ErrorPlant", t.toString())
                }
            })
    }
}