package com.nicoqueijo.android.selectcurrency.usecases

import android.content.Context
import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.ui.extensions.getStringResourceByName
import java.util.Locale
import javax.inject.Inject

class FilterCurrenciesUseCase @Inject constructor(
    private val context: Context,
    private val retrieveCurrenciesUseCase: RetrieveCurrenciesUseCase,
) {
    suspend operator fun invoke(searchTerm: String): List<Currency> {
        val filteredCurrencies = mutableListOf<Currency>()
        if (searchTerm.isEmpty()) {
            filteredCurrencies.addAll(retrieveCurrenciesUseCase.invoke())
        } else {
            val trimmedSearchTerm = searchTerm.lowercase(Locale.ROOT).trim { it <= ' ' }
            val allCurrencies = retrieveCurrenciesUseCase.invoke()
            allCurrencies.forEach { currency ->
                val currencyCode = currency.trimmedCurrencyCode.lowercase(Locale.ROOT)
                val currencyName = context.getStringResourceByName(
                    name = currency.currencyCode
                ).lowercase(Locale.ROOT)
                if (currencyCode.contains(trimmedSearchTerm) || currencyName.contains(trimmedSearchTerm)) {
                    filteredCurrencies.add(currency)
                }
            }
        }
        return filteredCurrencies
    }
}
