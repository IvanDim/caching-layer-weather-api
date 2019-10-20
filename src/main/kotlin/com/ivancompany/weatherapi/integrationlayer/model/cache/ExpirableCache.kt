package com.ivancompany.weatherapi.integrationlayer.model.cache

import com.google.gson.Gson
import com.ivancompany.weatherapi.integrationlayer.model.pojo.openWeatherMapApi.ResponseData
import org.springframework.stereotype.Component
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.HashMap

@Component
class ExpirableCache : Cache {

    private val weatherTable = HashMap<Long, ResponseData>(100)
    private val idList = LinkedList<IdTimePair>()

    override val size: Int
        get() = weatherTable.size

    // O(1)
    override fun get(key: Long): ResponseData? {
        var data = weatherTable[key]
        if (data == null) {
            data = requestData(key)
                    ?: return null
            push(key, data)
        }

        return data
    }

    override fun getCache(): HashMap<Long, ResponseData> {
        return weatherTable
    }

    // O(1)
    override fun push(key: Long, value: ResponseData) {
        idList.push(IdTimePair(key, System.currentTimeMillis()))
        weatherTable[key] = value
    }

    // O(1)
    override fun poll(): ResponseData? {
        val polled = idList.pollLast()
        if (polled != null)
            return weatherTable.remove(polled.key)
        return null
    }

    override fun peekCache(): ResponseData? {
        if (!idList.isEmpty()) {
            val keyTimePair = idList.peekLast()
            return weatherTable[keyTimePair.key]
        }
        return null
    }

    override fun peekList(): IdTimePair? {
        return idList.peekLast()
    }

    override fun clear() {
        idList.clear()
        weatherTable.clear()
    }

    /**
     * If the requested data is not in the cache it is fetched from the 3rd party API
     */
    override fun requestData(cityId: Long): ResponseData? {

        var result = ""
        val url = URL("http://api.openweathermap.org/data/2.5/forecast?id=$cityId&appid=81c816d0bf4706dd981d8f12e381b555&units=metric\n")

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"

            println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")
            if (responseCode != 200)
                return null

            inputStream.bufferedReader().use {
                result = it.readLine()
            }
        }

        val response = Gson().fromJson(result, ResponseData::class.java)
        println(response)
        return response
    }
}
