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
            return addCurrency(
                currenciesA = memoryCurrenciesCopy,
                currenciesB = databaseCurrenciesCopy,
            )
        } else if (databaseCurrenciesCopy.size < memoryCurrenciesCopy.size) {
            return removeCurrency(
                currenciesA = memoryCurrenciesCopy,
                currenciesB = databaseCurrenciesCopy,
            )
        } else {
            return reorderCurrencies(
                currenciesA = memoryCurrenciesCopy,
                currenciesB = databaseCurrenciesCopy,
            )
        }
    }

    private fun addCurrency(
        currenciesA: List<Currency>,
        currenciesB: List<Currency>,
    ): List<Currency> {
        return (currenciesA + currenciesB).distinctBy { currency ->
            currency.currencyCode
        }
    }

    private fun removeCurrency(
        currenciesA: List<Currency>,
        currenciesB: List<Currency>,
    ): List<Currency> {
        return currenciesA.filter { currencyA ->
            currenciesB.any { currencyB ->
                currencyA.currencyCode == currencyB.currencyCode
            }
        }.onEach { currencyA ->
            currencyA.position = currenciesB.first { currencyB ->
                currencyB.currencyCode == currencyA.currencyCode
            }.position
        }
    }

    private fun reorderCurrencies(
        currenciesA: List<Currency>,
        currenciesB: List<Currency>,
    ): List<Currency> {
        return currenciesA.onEach { currencyA ->
            val correspondingCurrencyB = currenciesB.first { currencyB ->
                currencyB.currencyCode == currencyA.currencyCode
            }
            currencyA.position = correspondingCurrencyB.position
        }.sortedBy { it.position }
    }
}
