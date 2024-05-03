package com.nicoqueijo.android.network

/**
 * https://docs.openexchangerates.org/docs/latest-json
 */
data class ApiEndPoint(
    var timestamp: Long = 0L,

    var exchangeRates: ExchangeRates? = null
)
