package com.ivancompany.weatherapi.integrationlayer.model.pojo.openWeatherMapApi

data class MainData(
        var temp: Double = 0.0,
        var temp_min: Double = 0.0,
        var temp_max: Double = 0.0,
        var pressure: Double = 0.0,
        var sea_level: Double = 0.0,
        var grnd_level: Double = 0.0,
        var humidity: Int = 0,
        var temp_kf: Double = 0.0
)