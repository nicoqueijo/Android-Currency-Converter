package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.core.CurrencyConverter
import com.nicoqueijo.android.core.extensions.deepCopy
import com.nicoqueijo.android.core.extensions.roundToFourDecimalPlaces
import java.math.BigDecimal

class UpdateHintsUseCase {

    operator fun invoke(
        currencies: List<Currency>,
    ): List<Currency> {
        val currenciesCopy = currencies.deepCopy()
        if (currenciesCopy.isEmpty()) { // No currencies to process
            return currenciesCopy
        }
        val focusedCurrency = currenciesCopy.single { it.isFocused }
        focusedCurrency.conversion.hint = "1"
        currenciesCopy.filter { it.currencyCode != focusedCurrency.currencyCode }
            .forEach { currency ->
                val fromRate = focusedCurrency.exchangeRate
                val toRate = currency.exchangeRate
                val conversionValue = CurrencyConverter.convertCurrency(
                    amount = BigDecimal("1"),
                    fromRate = fromRate,
                    toRate = toRate
                ).roundToFourDecimalPlaces().toString()
                currency.conversion.hint = conversionValue
            }
        return currenciesCopy
    }
}