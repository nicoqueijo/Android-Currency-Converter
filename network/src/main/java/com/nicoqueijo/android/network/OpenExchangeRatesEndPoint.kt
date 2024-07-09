package com.nicoqueijo.android.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing the JSON response from the Open Exchange Rates API.
 * https://docs.openexchangerates.org/docs/latest-json
 *
 * This class is designed to map the response from the Open Exchange Rates API, which includes a timestamp
 * and an object containing the exchange rates for various currencies against the USD.
 *
 * @property timestamp The timestamp of when the exchange rates were fetched.
 * @property exchangeRates An [ExchangeRates] object containing the exchange rates for various currencies.
 */
@Serializable
data class OpenExchangeRatesEndPoint(
    @SerialName("timestamp")
    var timestamp: Long = 0L,
    @SerialName("rates")
    var exchangeRates: ExchangeRates? = null
)
