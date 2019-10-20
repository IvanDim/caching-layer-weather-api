package com.ivancompany.weatherapi.integrationlayer.model.pojo.openWeatherMapApi

data class ResponseData(
        var cod: Int = 0,
        var cnt: Int = 0,
        var list: List<WeatherItem> = ArrayList(),
        var city: City
)