package com.nicoqueijo.android.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * https://docs.openexchangerates.org/docs/latest-json
 */
@Serializable
data class OpenExchangeRatesEndPoint(
    @SerialName("timestamp")
    var timestamp: Long = 0L,
    @SerialName("rates")
    var exchangeRates: ExchangeRates? = null
)
