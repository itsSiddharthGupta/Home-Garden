package com.example.homegarden.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.homegarden.R
import com.example.homegarden.databinding.ActivityHomeBinding
import com.example.homegarden.dataclasses.UserProfile
import com.example.homegarden.dataclasses.WeatherToday
import com.example.homegarden.viewmodels.HomeViewModel
import com.example.homegarden.viewmodels.HomeViewModelFactory
import com.google.gson.Gson
import kotlin.math.roundToInt

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val userProfile = Gson().fromJson<UserProfile>(
            getSharedPreferences(
                "user",
                MODE_PRIVATE
            ).getString("profile", null), UserProfile::class.java
        )
        val factory = HomeViewModelFactory(userProfile.cityName)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        binding.homeViewModel = viewModel
        binding.lifecycleOwner = this
        binding.cardPlantIdentify.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    FlowerDetectionActivity::class.java
                )
            )
        }
        binding.cardRoomPlants.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MyPlantsActivity::class.java
                ).putExtra("IS-INDOOR", true)
            )
        }
        binding.cardOutdoorPlants.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MyPlantsActivity::class.java
                ).putExtra("IS-INDOOR", false)
            )
        }
        binding.constraintLayoutProfile.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MyProfileActivity::class.java
                )
            )
        }
        viewModel.weatherLiveData.observe(this,
            { t ->
                if (t != null)
                    setWeatherFields(t)
            })

        viewModel.isWeatherLoading.observe(this,
            { t ->
                if (t != null) {
                    Log.e("IsLoading", t.toString())
                    if (t) {
                        binding.shimmerLayout.visibility = View.VISIBLE
                        binding.weatherTodayLinearLayout.visibility = View.GONE
                    } else {
                        binding.weatherTodayLinearLayout.visibility = View.VISIBLE
                        binding.shimmerLayout.visibility = View.GONE
                    }
                }
            })
    }

    private fun setWeatherFields(weatherToday: WeatherToday) {
        //todo 1. cache the weather for 1 day = 1 call
        binding.txtTemperature.text = "${weatherToday.main?.temp?.roundToInt()} °c"
        binding.txtHumidity.text = "${weatherToday.main?.humidity}%"
        binding.progressHumidity.progress = weatherToday.main?.humidity!!
        binding.txtMainWeather.text = weatherToday.weather?.get(0)?.main
        binding.txtHumidityDesc.text = when (weatherToday.main?.humidity) {
            in 80..100 -> "High moisture in air"
            in 40..80 -> "Good moisture. Less water req."
            else -> "Low moisture. Water the plants."
        }
        Glide.with(this)
            .load("http://openweathermap.org/img/wn/${weatherToday.weather?.get(0)?.icon}@2x.png")
            .into(binding.imgWeatherIcon)
    }
}