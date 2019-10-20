package com.ivancompany.weatherapi.integrationlayer.service

import com.google.gson.Gson
import com.ivancompany.weatherapi.integrationlayer.model.cache.ExpirableCache
import com.ivancompany.weatherapi.integrationlayer.model.pojo.openWeatherMapApi.ResponseData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.HttpURLConnection
import java.net.URL
import javax.annotation.PostConstruct

@Service
class CacheManager(
        var cache: ExpirableCache
) {
    @Value("\${cache.lifespan}")
    private val lifespan: Long = 60

    @Value("\${cache.checkCycle}")
    private val checkCycle: Long = 10

    /**
     * Starts the coroutine checking for old data in the cache
     * It loops every "checkCycle" seconds
     */
    @PostConstruct
    fun initCoroutines() {
        GlobalScope.launch {
            while (true) {
                delay(checkCycle /*seconds*/ * 1000 /*milliseconds*/)
                cleanOldData()
            }
        }
    }

    /**
     * Cleans data that is older than "lifespan"
     */
    fun cleanOldData() {
        while (true) {
            val cacheTail = cache.peekList()
            if (cacheTail != null) {
                val timeForecast = cacheTail.time
                val shouldRecycle = System.currentTimeMillis() - timeForecast > lifespan /*seconds*/ * 1000L /*milliseconds*/
                if (shouldRecycle)
                    cache.poll()
                else break
            } else break
        }
    }
}