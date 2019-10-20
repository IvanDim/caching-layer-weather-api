package com.ivancompany.weatherapi.integrationlayer.model.pojo.openWeatherMapApi

data class WeatherItem(
        var dt: Long = 0,
        var main: MainData,
        var weather: List<WeatherData>,
        var dt_txt: String = ""
)