package com.example.homegarden.dataclasses

data class WeatherToday(
    var coord: Coordinates? = null,
    var weather: List<Weather>? = null,
    var main: Main? = null,
    var cod: Int? = null
) {
    data class Coordinates(val lon: Double, val lat: Double)
    data class Weather(
        val id: Long,
        val main: String,
        val description: String,
        val icon: String
    )

    data class Main(val temp: Double, val humidity: Int)
}