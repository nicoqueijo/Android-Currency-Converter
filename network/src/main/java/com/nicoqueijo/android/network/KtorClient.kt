package com.nicoqueijo.android.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient {

    private val client = HttpClient(Android) {
        defaultRequest { url(BASE_URL) }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    suspend fun getExchangeRates(): OpenExchangeRatesEndPoint {
        val apiKey = when (BuildConfig.BUILD_TYPE) {
            "debug" -> BuildConfig.API_KEY_DEBUG
            else -> BuildConfig.API_KEY_RELEASE
        }
        return client.get("latest.json?app_id=$apiKey").body<OpenExchangeRatesEndPoint>()
    }

    companion object {
        const val BASE_URL = "https://openexchangerates.org/api/"
    }
}