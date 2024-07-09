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

/**
 * A client class for making network requests to the Open Exchange Rates API using Ktor.
 *
 * This class sets up an [HttpClient] with necessary configurations and provides methods to fetch exchange rates.
 */
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

    /**
     * Fetches the latest exchange rates from the Open Exchange Rates API.
     *
     * @return An [ApiOperation] containing either the fetched [OpenExchangeRatesEndPoint] data or an exception.
     */
    suspend fun getExchangeRates(): ApiOperation<OpenExchangeRatesEndPoint> {
        val apiKey = when (BuildConfig.BUILD_TYPE) {
            "debug" -> BuildConfig.API_KEY_DEBUG
            else -> BuildConfig.API_KEY_RELEASE
        }
        return makeApiCall {
            val response = client.get(
                urlString = "latest.json?app_id=$apiKey"
            )
            if (response.status.value != 200) {
                throw Exception("Failed to fetch exchange rates.")
            }
            response.body<OpenExchangeRatesEndPoint>()
        }
    }

    /**
     * Makes an API call and returns the result as an [ApiOperation].
     *
     * @param apiCall A lambda function representing the API call to be made.
     * @return An [ApiOperation] containing either the result of the API call or an exception.
     */
    private inline fun <T> makeApiCall(apiCall: () -> T): ApiOperation<T> {
        return try {
            ApiOperation.Success(data = apiCall())
        } catch (exception: Exception) {
            ApiOperation.Failure(exception = exception)
        }
    }

    companion object {
        const val BASE_URL = "https://openexchangerates.org/api/"
    }
}

/**
 * A sealed interface representing the result of an API operation.
 *
 * @param T The type of data returned by the API operation.
 */
sealed interface ApiOperation<T> {
    /**
     * Represents a successful API operation.
     *
     * @property data The data returned by the API operation.
     */
    data class Success<T>(val data: T) : ApiOperation<T>

    /**
     * Represents a failed API operation.
     *
     * @property exception The exception thrown during the API operation.
     */
    data class Failure<T>(val exception: Exception) : ApiOperation<T>

    /**
     * Executes the given block if the operation was successful.
     *
     * @param block A suspend function to be executed with the data if the operation was successful.
     * @return The current [ApiOperation] instance which can be used for chaining.
     */
    suspend fun onSuccess(block: suspend (T) -> Unit): ApiOperation<T> {
        if (this is Success) {
            block(data)
        }
        return this
    }

    /**
     * Executes the given block if the operation failed.
     *
     * @param block A function to be executed with the exception if the operation failed.
     * @return The current [ApiOperation] instance which can be used for chaining.
     */
    fun onFailure(block: (Exception) -> Unit): ApiOperation<T> {
        if (this is Failure) {
            block(exception)
        }
        return this
    }
}