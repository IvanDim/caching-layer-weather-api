package com.ivancompany.weatherapi.integrationlayer.model.pojo.clientAppApi.nextDay

import com.ivancompany.weatherapi.integrationlayer.model.pojo.clientAppApi.convertTemp
import com.ivancompany.weatherapi.integrationlayer.model.pojo.openWeatherMapApi.ResponseData
import com.ivancompany.weatherapi.integrationlayer.model.pojo.openWeatherMapApi.WeatherItem
import java.util.*

class NextDayForecast {

    // the id of the location
    var locationId: Long = 0

    // time of the forecast (the time is chosen by highest temperature for the day)
    var dt: Long = 0

    // temperature for the specific time and location
    var temperature: Double = -100.0

    // unit of the temperature
    var unit = "celsius"

    /**
     * Parsing the data coming from OpenWeatherMap to NextDayForecast
     */
    fun parseData(data: ResponseData, unit: String?): NextDayForecast {
        locationId = data.city.id
        findTimeAndTemp(data.list)

        if (unit == "fahrenheit")
            this.unit = unit

        // Convert the temperature to the required unit (default: celsius)
        temperature = convertTemp(temperature, this.unit)

        return this
    }

    /**
     * Checks every set of data for the next day and finds the highest temperature with the exact time
     *
     */
    private fun findTimeAndTemp(data: List<WeatherItem>) {
        val calendar = Calendar.getInstance()
        calendar.time = Date(System.currentTimeMillis())
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        for (element in data) {
            calendar.time = Date(element.dt * 1000)
            val forecastDay = calendar.get(Calendar.DAY_OF_MONTH)
            if ((forecastDay == currentDay + 1) && (temperature < element.main.temp)) {
                temperature = element.main.temp
                dt = element.dt
            } else if (forecastDay == currentDay + 2)
                break
        }
    }
}