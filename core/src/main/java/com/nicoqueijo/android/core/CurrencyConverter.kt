package com.nicoqueijo.android.core

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * A utility class for converting currencies.
 */
object CurrencyConverter {

    /**
     * Converts currency from one rate to another.
     *
     * @param amount The amount of currency to convert.
     * @param fromRate The exchange rate of the source currency.
     * @param toRate The exchange rate of the target currency.
     * @return The converted amount.
     */
    fun convertCurrency(amount: BigDecimal, fromRate: Double, toRate: Double): BigDecimal {
        val valueInDollars = convertAnyCurrencyToDollar(amount, fromRate)
        return convertDollarToAnyCurrency(valueInDollars, toRate)
    }

    private fun convertAnyCurrencyToDollar(amount: BigDecimal, fromRate: Double): BigDecimal {
        val scale = 50
        return amount.divide(BigDecimal.valueOf(fromRate), scale, RoundingMode.HALF_UP)
    }

    private fun convertDollarToAnyCurrency(dollarValue: BigDecimal, toRate: Double): BigDecimal {
        return dollarValue.multiply(BigDecimal.valueOf(toRate))
    }
}
