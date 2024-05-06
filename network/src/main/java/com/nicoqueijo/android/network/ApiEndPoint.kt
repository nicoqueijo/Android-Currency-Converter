package com.nicoqueijo.android.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * https://docs.openexchangerates.org/docs/latest-json
 */
@Serializable
class ApiEndPoint {
    var timestamp: Long = 0L

    @SerialName("rates")
    var exchangeRates: ExchangeRates? = null
}
