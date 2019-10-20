package com.ivancompany.weatherapi.integrationlayer.model.pojo.openWeatherMapApi

data class WeatherData(
        var id: Long = 0,
        var main: String = "",
        var description: String = "",
        var icon: String = ""
)