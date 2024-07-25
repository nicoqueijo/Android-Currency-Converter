package com.nicoqueijo.android.network

import com.nicoqueijo.android.network.model.ApiOperation
import com.nicoqueijo.android.network.model.OpenExchangeRatesEndPoint
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

/**
 * A client class for making network requests to the Open Exchange Rates API using Ktor. Sets up an
 * [HttpClient] with necessary configurations and provides methods to fetch exchange rates.
 *
 * @property httpClient The [HttpClient] instance used for making network requests.
 */
class KtorClient @Inject constructor(
    private val httpClient: HttpClient,
) {

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
            val response = httpClient.get(
                urlString = "latest.json?app_id=$apiKey"
            )
            if (response.status != HttpStatusCode.OK) {
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
}
