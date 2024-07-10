package com.nicoqueijo.android.selectcurrency.usecases

import android.content.Context
import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.ui.extensions.getStringResourceByName
import java.util.Locale
import javax.inject.Inject

/**
 * Use case to filter currencies based on a search term.
 *
 * This class interacts with the [RetrieveCurrenciesUseCase] to retrieve all currencies
 * and filters them based on the provided search term. If the search term is empty,
 * it returns all currencies.
 *
 * @property context The context used to access string resources for currency names.
 * @property retrieveCurrenciesUseCase The use case to retrieve all currencies.
 */
class FilterCurrenciesUseCase @Inject constructor(
    private val context: Context,
    private val retrieveCurrenciesUseCase: RetrieveCurrenciesUseCase,
) {

    /**
     * Filters the list of currencies based on the provided search term.
     *
     * This method performs the following steps:
     * 1. If the search term is empty, retrieves and returns all currencies.
     * 2. Otherwise, trims and converts the search term to lowercase.
     * 3. Retrieves all currencies and filters them by checking if the currency code or name contains the search term.
     *
     * @param searchTerm The search term to filter currencies.
     * @return A list of currencies that match the search term.
     */
    suspend operator fun invoke(searchTerm: String): List<Currency> {
        val filteredCurrencies = mutableListOf<Currency>()
        if (searchTerm.isEmpty()) {
            filteredCurrencies.addAll(
                elements = retrieveCurrenciesUseCase.invoke()
            )
        } else {
            val trimmedSearchTerm = searchTerm.lowercase(Locale.ROOT)
                .trim { char -> char <= ' ' }
            val allCurrencies = retrieveCurrenciesUseCase.invoke()
            allCurrencies.forEach { currency ->
                val currencyCode = currency.trimmedCurrencyCode.lowercase(Locale.ROOT)
                val currencyName = context.getStringResourceByName(
                    name = currency.currencyCode
                ).lowercase(Locale.ROOT)
                if (
                    currencyCode.contains(trimmedSearchTerm) ||
                    currencyName.contains(trimmedSearchTerm)
                ) {
                    filteredCurrencies.add(currency)
                }
            }
        }
        return filteredCurrencies
    }
}
