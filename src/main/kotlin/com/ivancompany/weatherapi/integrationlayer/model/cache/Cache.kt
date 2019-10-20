package com.ivancompany.weatherapi.integrationlayer.model.cache

import com.ivancompany.weatherapi.integrationlayer.model.pojo.openWeatherMapApi.ResponseData

interface Cache {
    val size: Int

    operator fun get(key: Long): ResponseData?

    fun getCache(): HashMap<Long, ResponseData>

    fun push(key: Long, value: ResponseData)

    fun poll(): ResponseData?

    fun peekCache(): ResponseData?

    fun peekList(): IdTimePair?

    fun clear()

    fun requestData(cityId: Long): ResponseData?
}