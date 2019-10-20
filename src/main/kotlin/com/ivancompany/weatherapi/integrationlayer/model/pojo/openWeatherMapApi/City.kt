package com.ivancompany.weatherapi.integrationlayer.model.pojo.openWeatherMapApi

data class City(
        var id: Long = 0,
        var name: String = "",
        var country: String = "",
        var timezone: Long = 0,
        var sunrise: Long = 0,
        var sunset: Long = 0,
        var coord: Coordinates
)