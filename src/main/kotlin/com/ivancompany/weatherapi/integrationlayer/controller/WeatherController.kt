package com.ivancompany.weatherapi.integrationlayer.controller

import com.ivancompany.weatherapi.integrationlayer.model.cache.Cache
import com.ivancompany.weatherapi.integrationlayer.model.pojo.clientAppApi.fiveDays.FiveDaysForecast
import com.ivancompany.weatherapi.integrationlayer.model.pojo.clientAppApi.nextDay.NextDayForecast
import com.ivancompany.weatherapi.integrationlayer.model.pojo.openWeatherMapApi.ResponseData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/weather")
class WeatherController(
        @Autowired
        var cache: Cache
) {

    /**
     * Interface fetching data for a single location for the next 5 days
     * The client can choose unit for the temperature (celsius/fahrenheit)
     * The response contains the temperature on a specific location for every 3 hours in a period of 5 day
     */
    @GetMapping("/locations/{locationId}")
    fun getById(@PathVariable locationId: Long?, @RequestParam unit: String?): Any {

        if (locationId == null)
            return ResponseEntity("Location ID is required", HttpStatus.BAD_REQUEST)

        val data = cache[locationId] ?: return ResponseEntity("Location ID is not supported", HttpStatus.BAD_REQUEST)

        return FiveDaysForecast().parseData(data, unit)
    }

    /**
     * Interface fetching data for multiple locations for the next day
     * All the location IDs are comma separated in "locationId"
     * The client can choose unit for the temperature (celsius/fahrenheit)
     * The request returns the highest temperatures for every location in JSON format
     */
    @GetMapping("/summary")
    fun getByIdList(@RequestParam locations: String?, @RequestParam unit: String?): Any {

        if (locations == null || locations == "")
            return ResponseEntity("There are no location IDs", HttpStatus.BAD_REQUEST)

        val listCityId: List<String> = locations.split(",")
        val listData = ArrayList<Any>()
        for (item in listCityId) {
            var data: ResponseData?

            try {
                data = cache[item.toLong()]
            } catch (e: NumberFormatException) {
                return ResponseEntity("Invalid location IDs", HttpStatus.BAD_REQUEST)
            }

            var forecast: Any
            forecast = if (data != null) NextDayForecast().parseData(data, unit)
            else "There is a problem using the API"

            listData.add(forecast)
        }
        return listData
    }
}