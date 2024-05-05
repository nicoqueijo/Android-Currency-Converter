package com.nicoqueijo.android.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * https://docs.openexchangerates.org/docs/latest-json
 */
@JsonClass(generateAdapter = true)
class ApiEndPoint {
    @Json(name = "timestamp")
    var timestamp: Long = 0L

    @Json(name = "rates")
    var exchangeRates: ExchangeRates? = null
}
