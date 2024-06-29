package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.CurrencyConverter
import com.nicoqueijo.android.core.extensions.deepCopy
import com.nicoqueijo.android.core.model.Currency
import java.math.BigDecimal

class UpdateConversionsUseCase {

    operator fun invoke(
        currencies: List<Currency>,
    ): List<Currency> {
        val currenciesCopy = currencies.deepCopy()
        if (currenciesCopy.isEmpty()) { // No currencies to process
            return currenciesCopy
        }
        val focusedCurrency = currenciesCopy.single { it.isFocused }
        currenciesCopy.filter { it.currencyCode != focusedCurrency.currencyCode }
            .forEach { currency ->
                val fromRate = focusedCurrency.exchangeRate
                val toRate = currency.exchangeRate
                if (focusedCurrency.conversion.valueAsString.isNotEmpty()) {
                    val conversionValue = CurrencyConverter.convertCurrency(
                        amount = BigDecimal(focusedCurrency.conversion.valueAsString),
                        fromRate = fromRate,
                        toRate = toRate
                    )
                    currency.conversion.value = conversionValue
                } else {
                    currency.conversion.valueAsString = ""
                }
            }
        return currenciesCopy
    }
}