package com.nicoqueijo.android.core

import io.kotest.matchers.bigdecimal.shouldBeInRange
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.math.BigDecimal

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CurrencyConverterTest {

    /**
     * Pool of a few selected currencies for testing in the form of currency code to exchange rate.
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

    /**
     * Most traded currency pairings. Amount value of 100.
     */
    @Nested
    inner class MostTradedCurrencyPairings {
        @Test
        fun `given a EUR amount of 100, when converting it to USD, then the converted amount should be correct`() {
            val amount = BigDecimal("100")
            val from = exchangeRates["EUR"]!!
            val to = exchangeRates["USD"]!!
            val expected = BigDecimal("118.6249")..BigDecimal("118.6250")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given a USD amount of 100, when converting it to JPY, then the converted amount should be correct`() {
            val amount = BigDecimal("100")
            val from = exchangeRates["USD"]!!
            val to = exchangeRates["JPY"]!!
            val expected = BigDecimal("10470.5027")..BigDecimal("10470.5028")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given a GBP amount of 100, when converting it to USD, then the converted amount should be correct`() {
            val amount = BigDecimal("100")
            val from = exchangeRates["GBP"]!!
            val to = exchangeRates["USD"]!!
            val expected = BigDecimal("130.4549")..BigDecimal("130.4550")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given a AUD amount of 100, when converting it to USD, then the converted amount should be correct`() {
            val amount = BigDecimal("100")
            val from = exchangeRates["AUD"]!!
            val to = exchangeRates["USD"]!!
            val expected = BigDecimal("71.3899")..BigDecimal("71.3900")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given a USD amount of 100, when converting it to CAD, then the converted amount should be correct`() {
            val amount = BigDecimal("100")
            val from = exchangeRates["USD"]!!
            val to = exchangeRates["CAD"]!!
            val expected = BigDecimal("131.2575")..BigDecimal("131.2576")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
            fun `given a USD amount of 100, when converting it to CNY, then the converted amount should be correct`() {
            val amount = BigDecimal("100")
            val from = exchangeRates["USD"]!!
            val to = exchangeRates["CNY"]!!
            val expected = BigDecimal("668.6800")..BigDecimal("668.6801")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given a USD amount of 100, when converting it to CHF, then the converted amount should be correct`() {
            val amount = BigDecimal("100")
            val from = exchangeRates["USD"]!!
            val to = exchangeRates["CHF"]!!
            val expected = BigDecimal("90.4143")..BigDecimal("90.4144")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given a USD amount of 100, when converting it to HKD, then the converted amount should be correct`() {
            val amount = BigDecimal("100")
            val from = exchangeRates["USD"]!!
            val to = exchangeRates["HKD"]!!
            val expected = BigDecimal("775.0215")..BigDecimal("775.0216")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given a EUR amount of 100, when converting it to GBP, then the converted amount should be correct`() {
            val amount = BigDecimal("100")
            val from = exchangeRates["EUR"]!!
            val to = exchangeRates["GBP"]!!
            val expected = BigDecimal("90.9317")..BigDecimal("90.9318")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given a USD amount of 100, when converting it to KRW, then the converted amount should be correct`() {
            val amount = BigDecimal("100")
            val from = exchangeRates["USD"]!!
            val to = exchangeRates["KRW"]!!
            val expected = BigDecimal("112844.5000")..BigDecimal("112844.5001")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }
    }


    /**
     * Currencies with large exchange rate difference.
     */
    @Nested
    inner class LargeExchangeRateDifference {
        @Test
        fun `given a high amount of BTC, when converting it to VEF, then the converted amount should be correct`() {
            val amount = BigDecimal("99999999999999999999.9999")
            val from = exchangeRates["BTC"]!!
            val to = exchangeRates["VEF"]!!
            val expected = BigDecimal("323054853361164842145858305583.0682")..BigDecimal("323054853361164842145858305583.0683")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given a low amount of VEF, when converting it to BTC, then the converted amount should be correct`() {
            val amount = BigDecimal("0.0001")
            val from = exchangeRates["VEF"]!!
            val to = exchangeRates["BTC"]!!
            val expected = BigDecimal("0.0000")..BigDecimal("0.0001")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }
    }


    /**
     * Random currency pairings and random amount values.
     */
    @Nested
    inner class RandomCurrencyPairings {
        @Test
        fun `given a zero amount of CNY, when converting it to AED, then the converted amount should be correct`() {
            val amount = BigDecimal("0")
            val from = exchangeRates["CNY"]!!
            val to = exchangeRates["AED"]!!
            val expected = 0.0000
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            ).toDouble()
            actual.shouldBe(expected)
        }

        @Test
        fun `given some amount of SGD, when converting it to JPY, then the converted amount should be correct`() {
            val amount = BigDecimal("0.32")
            val from = exchangeRates["SGD"]!!
            val to = exchangeRates["JPY"]!!
            val expected = BigDecimal("24.6709")..BigDecimal("24.6710")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given some amount of INR, when converting it to VEF, then the converted amount should be correct`() {
            val amount = BigDecimal("1")
            val from = exchangeRates["INR"]!!
            val to = exchangeRates["VEF"]!!
            val expected = BigDecimal("3365.4838")..BigDecimal("3365.4839")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given some amount of RUB, when converting it to XAG, then the converted amount should be correct`() {
            val amount = BigDecimal("13.99")
            val from = exchangeRates["RUB"]!!
            val to = exchangeRates["XAG"]!!
            val expected = BigDecimal("0.0074")..BigDecimal("0.0075")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given some amount of HKD, when converting it to MXN, then the converted amount should be correct`() {
            val amount = BigDecimal("2567")
            val from = exchangeRates["HKD"]!!
            val to = exchangeRates["MXN"]!!
            val expected = BigDecimal("6910.3213")..BigDecimal("6910.3214")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given some amount of COP, when converting it to BTC, then the converted amount should be correct`() {
            val amount = BigDecimal("833984.1797")
            val from = exchangeRates["COP"]!!
            val to = exchangeRates["BTC"]!!
            val expected = BigDecimal("0.0169")..BigDecimal("0.0170")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given some amount of CAD, when converting it to AUD, then the converted amount should be correct`() {
            val amount = BigDecimal("24612696.7")
            val from = exchangeRates["CAD"]!!
            val to = exchangeRates["AUD"]!!
            val expected = BigDecimal("26266237.8846")..BigDecimal("26266237.8847")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given some amount of XAU, when converting it to USD, then the converted amount should be correct`() {
            val amount = BigDecimal("12345.6789")
            val from = exchangeRates["XAU"]!!
            val to = exchangeRates["USD"]!!
            val expected = BigDecimal("23478014.0346")..BigDecimal("23478014.0347")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given some amount of CHF, when converting it to EUR, then the converted amount should be correct`() {
            val amount = BigDecimal("4300")
            val from = exchangeRates["CHF"]!!
            val to = exchangeRates["EUR"]!!
            val expected = BigDecimal("4009.1776")..BigDecimal("4009.1777")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given some amount of BRL, when converting it to KRW, then the converted amount should be correct`() {
            val amount = BigDecimal("30156.3")
            val from = exchangeRates["BRL"]!!
            val to = exchangeRates["KRW"]!!
            val expected = BigDecimal("6055918.4762")..BigDecimal("6055918.4763")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }

        @Test
        fun `given some amount of GBP, when converting it to ARS, then the converted amount should be correct`() {
            val amount = BigDecimal("49922182058874209463.4158")
            val from = exchangeRates["GBP"]!!
            val to = exchangeRates["ARS"]!!
            val expected = BigDecimal("5087649048143592730405.1985")..BigDecimal("5087649048143592730405.1986")
            val actual = CurrencyConverter.convertCurrency(
                amount = amount,
                fromRate = from,
                toRate = to
            )
            actual.shouldBeInRange(expected)
        }
    }
}
