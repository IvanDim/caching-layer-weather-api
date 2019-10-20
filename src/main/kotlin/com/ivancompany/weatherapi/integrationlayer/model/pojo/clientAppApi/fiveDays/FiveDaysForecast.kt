package com.ivancompany.weatherapi.integrationlayer.model.pojo.clientAppApi.fiveDays

import com.ivancompany.weatherapi.integrationlayer.model.pojo.clientAppApi.convertTemp
import com.ivancompany.weatherapi.integrationlayer.model.pojo.openWeatherMapApi.ResponseData

class FiveDaysForecast {

    // list of temperatures for every 3 hours
    val listTemperatures = ArrayList<WeatherInfo>()

    // the id of the location
    var locationId: Long? = null

    // unit to be used
    var unit = "celsius"

    /**
     * Parsing the data coming from OpenWeatherMap to FiveDaysForecast
     */
    fun parseData(data: ResponseData, unit: String?): FiveDaysForecast {
        locationId = data.city.id

        if (unit == "fahrenheit")
            this.unit = unit

        for (item in data.list) {
            listTemperatures.add(WeatherInfo(item.dt, convertTemp(item.main.temp, this.unit)))
        }

        return this
    }
}