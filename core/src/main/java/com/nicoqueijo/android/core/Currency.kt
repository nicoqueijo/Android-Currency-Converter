package com.nicoqueijo.android.core

/**
 * Represents a currency with its code and exchange rate.
 *
 * @property currencyCode The three-letter ISO 4217 currency code.
 * @property exchangeRate The exchange rate of this currency to one US Dollar (USD).
 */
data class Currency(
    val currencyCode: String,
    val exchangeRate: Double
)
