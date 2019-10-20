package com.ivancompany.weatherapi.integrationlayer.model.pojo.clientAppApi

import kotlin.math.roundToInt

/**
 * Checks the unit and converts celsius in fahrenheit
 */
fun convertTemp(temperature: Double, unit: String): Double {
    return if (unit == "fahrenheit")
        return ((temperature * 1.8 + 32) * 100.0).roundToInt() / 100.0
    else
        temperature
}