package com.example.homegarden.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.homegarden.R
import com.example.homegarden.databinding.ActivityHomeBinding
import com.example.homegarden.dataclasses.PlantBasicInfo
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
                )
            )
        }
        viewModel.weatherLiveData.observe(this,
            { t ->
                if (t != null)
                    setWeatherFields(t)
            })
    }

    private fun setWeatherFields(weatherToday: WeatherToday) {
        //todo 1. cache the weather for 1 day = 1 call
        //todo 2. use shimmer layout for loading ui
        binding.txtTemperature.text = "${weatherToday.main?.temp?.roundToInt()} °c"
        binding.txtHumidity.text = "${weatherToday.main?.humidity}%"
        binding.progressHumidity.progress = weatherToday.main?.humidity!!
        binding.txtMainWeather.text = weatherToday.weather?.get(0)?.main
        binding.txtHumidityDesc.text = when (weatherToday.main?.humidity) {
            in 80..100 -> "High moisture in air"
            in 40..80 -> "Good moisture. Less water req."
            else -> "Low moisture. Water the plants."
        }
        Glide.with(this).load("http://openweathermap.org/img/wn/${weatherToday.weather?.get(0)?.icon}@2x.png").into(binding.imgWeatherIcon)
    }

    private fun getStaticPlantsBasicInfo(): List<PlantBasicInfo> {
        return listOf(
            PlantBasicInfo(
                1,
                "Peace Lily",
                "Peace Lilly is an indoor plant that has become universally aligned with these aspirations.",
                R.drawable.peace_lily
            ),
            PlantBasicInfo(
                2,
                "Aloe Vera",
                "Aloe Vera is also called the “Miracle plant”. There are good reasons for this. It’s renowned worldwide for having medicinal properties  that  rejuvenate, soothe and heal the human body.",
                R.drawable.aloe_vera
            ),
            PlantBasicInfo(
                3,
                "Ferns",
                "Ferns are counted among one of the oldest species of plants on earth, dating back to prehistoric times. Ferns belong to a group of vascular seeds, that bear neither flowers nor seeds.",
                R.drawable.nephrolepis_exaltata_aurea_pivla_fern
            ),
            PlantBasicInfo(
                4,
                "Green Spider Plant",
                "Spider plants have leaves that look like blades of grass with streaks of different colors either in the center or at the edges.",
                R.drawable.spider_plant
            ),
            PlantBasicInfo(
                5,
                "Indian Basil",
                "It’s been grown in India from as far back as 5,000 years. No wonder then, that Indians harbour a deep cultural and religious attachment with the plant from time immemorial.",
                R.drawable.holy_basil
            )
        )
    }

//    private fun getPlantsBasicInfo() : List<PlantBasicInfo>{
//        return listOf(
//            PlantBasicInfo(1, "Peace Lily", "Peace Lilly is an indoor plant that has become universally aligned with these aspirations.","https://plantdecors.com/wp-content/uploads/2017/11/Peace-Lilly-indoor-876x1024.jpg"),
//            PlantBasicInfo(2, "Aloe Vera", "Aloe Vera is also called the “Miracle plant”. There are good reasons for this. It’s renowned worldwide for having medicinal properties  that  rejuvenate, soothe and heal the human body.","https://plantdecors.com/blogs/wp-content/uploads/2018/03/Aloe-Vera.jpg"),
//            PlantBasicInfo(3, "Ferns", "Ferns are counted among one of the oldest species of plants on earth, dating back to prehistoric times. Ferns belong to a group of vascular seeds, that bear neither flowers nor seeds.", "https://plantdecors.com/blogs/wp-content/uploads/2018/03/Boston-Fern-Plant-in-a-Pot-1.jpg"),
//            PlantBasicInfo(4, "Green Spider Plant", "Spider plants have leaves that look like blades of grass with streaks of different colors either in the center or at the edges.", "https://plantdecors.com/blogs/wp-content/uploads/2018/03/hortikultura.mk_chlorophytum_5.jpg"),
//            PlantBasicInfo(5, "Indian Basil", "It’s been grown in India from as far back as 5,000 years. No wonder then, that Indians harbour a deep cultural and religious attachment with the plant from time immemorial.", "https://plantdecors.com/blogs/wp-content/uploads/2018/03/0J6A0699.jpg")
//        )
//    }
}