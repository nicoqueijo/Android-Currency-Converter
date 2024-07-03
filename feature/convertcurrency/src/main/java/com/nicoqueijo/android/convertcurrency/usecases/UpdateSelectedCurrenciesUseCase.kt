package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.extensions.deepCopy
import com.nicoqueijo.android.core.model.Currency

class UpdateSelectedCurrenciesUseCase {

    operator fun invoke(
        memoryCurrencies: List<Currency>,
        databaseCurrencies: List<Currency>,
    ): List<Currency> {
        val memoryCurrenciesCopy = memoryCurrencies.deepCopy()
        val databaseCurrenciesCopy = databaseCurrencies.deepCopy()
        if (databaseCurrenciesCopy.isEmpty()) {
            return emptyList()
        } else if (databaseCurrenciesCopy.size > memoryCurrenciesCopy.size) {
            // Addition of currency
            val newList = (memoryCurrenciesCopy + databaseCurrenciesCopy).distinctBy { currency ->
                currency.currencyCode
            }
            return newList
        } else if (databaseCurrenciesCopy.size < memoryCurrenciesCopy.size) {
            // Subtraction of currency
            // Fix bug: New position is not being copied into memoryCurrenciesCopy
            val newsList = memoryCurrenciesCopy.filter { memoryCurrency ->
                databaseCurrenciesCopy.any { databaseCurrency ->
                    memoryCurrency.currencyCode == databaseCurrency.currencyCode
                }
            }
            newsList.forEach { currency ->
                currency.position = databaseCurrenciesCopy.first { it.currencyCode == currency.currencyCode }.position
            }
            return newsList
        } else {
            // Reordering of currencies
            memoryCurrenciesCopy.forEach { memoryCurrency ->
                val correspondingDatabaseCurrency = databaseCurrenciesCopy.first { databaseCurrency ->
                        databaseCurrency.currencyCode == memoryCurrency.currencyCode
                    }
                memoryCurrency.position = correspondingDatabaseCurrency.position
            }
            return memoryCurrenciesCopy.sortedBy { it.position }
        }

        /**
         * if databaseCurrencies == 0 we remove all of them (already solved)
         *
         * if databaseCurrencies > memoryCurrencies we're adding a new currency. We can combine both.
         *
         * if databaseCurrencies < memoryCurrencies we're removing a currency. We can figure
         * out which databaseCurrency is not in memoryCurrencies and remove that one from memoryCurrencies
         *
         * if databaseCurrencies == memoryCurrencies we're reordering the positions. databaseCurrencies
         * and memoryCurrencies have the same currencies but it different order.
         */
    }
}
