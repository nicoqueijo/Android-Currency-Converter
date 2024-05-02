package com.nicoqueijo.android.core

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class CurrencyConverterTest {

    /**
     * Pool of a few selected currencies for testing.
     * Currency Code to Exchange Rate mapping.
     */
    private val exchangeRates = mapOf(
        "AED" to 3.673, // Emirati Dirham
        "ARS" to 78.120127, // Argentine Peso
        "AUD" to 1.400757, // Australian Dollar
        "BRL" to 5.619251, // Brazilian Real
        "BTC" to 0.000076918096, // Bitcoin
        "CAD" to 1.312575, // Canadian Dollar
        "CHF" to 0.904143, // Swiss Franc
        "CNY" to 6.6868, // Chinese Yuan Renminbi
        "COP" to 3778.616587, // Colombian Peso
        "EUR" to 0.842993, // Euro
        "GBP" to 0.766548, // British Pound
        "HKD" to 7.750215, // Hong Kong Dollar
        "INR" to 73.83415, // Indian Rupee
        "JPY" to 104.70502716, // Japanese Yen
        "KRW" to 1128.445, // South Korean Won
        "MXN" to 20.86345, // Mexican Peso
        "RUB" to 76.2173, // Russian Ruble
        "SGD" to 1.3581, // Singapore Dollar
        "USD" to 1.0, // United States Dollar
        "VEF" to 248487.642241, // Venezuelan Bolívar
        "XAG" to 0.04065871, // Silver
        "XAU" to 0.00052584, // Gold
    )

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun testCurrencyConversionFrom_EUR_To_USD() {
        val amount = BigDecimal("100")
        val from = exchangeRates["EUR"]
        val to = exchangeRates["USD"]
        val expected = BigDecimal("118.62494706361737286074736089149020217249728052308856")
        val actual = CurrencyConverter.convertCurrency(amount, from!!, to!!)

        actual.toDouble().shouldBe(
            expected.toDouble()
        )
    }
}