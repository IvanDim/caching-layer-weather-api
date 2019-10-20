package com.ivancompany.weatherapi.integrationlayer.model.pojo.clientAppApi.fiveDays

data class WeatherInfo(

        // time of the forecast
        var dt: Long = 0,

        // temperature for the specific time and location (default unit: celsius)
        var temperature: Double = 0.0
)