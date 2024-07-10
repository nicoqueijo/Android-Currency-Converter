package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.extensions.deepCopy
import com.nicoqueijo.android.core.model.Currency

/**
 * Use case to update the selected currencies based on a comparison between the current in-memory currencies
 * and the currencies stored in the database. It ensures synchronization between these two lists by adding,
 * removing, or reordering currencies as needed.
 */
class UpdateSelectedCurrenciesUseCase {

    /**
     * Updates the selected currencies based on the provided lists of in-memory and database currencies.
     *
     * This method performs the following steps:
     * 1. Creates deep copies of the provided lists of currencies.
     * 2. Determines if currencies need to be added, removed, or reordered.
     * 3. Calls the appropriate method to handle the detected change.
     *
     * @param memoryCurrencies The list of currencies currently in memory.
     * @param databaseCurrencies The list of currencies stored in the database.
     * @return A new list of currencies with updates applied to match the database state.
     */
    operator fun invoke(
        memoryCurrencies: List<Currency>,
        databaseCurrencies: List<Currency>,
    ): List<Currency> {
        val memoryCurrenciesCopy = memoryCurrencies.deepCopy()
        val databaseCurrenciesCopy = databaseCurrencies.deepCopy()
        if (databaseCurrenciesCopy.isEmpty()) {  // No currencies to process
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

    /**
     * Adds new currencies from the database list to the in-memory list.
     *
     * @param currenciesA The list of in-memory currencies.
     * @param currenciesB The list of database currencies.
     * @return A new list of currencies with added entries from the database, ensuring unique currency codes and sorted by position.
     */
    private fun addCurrency(
        currenciesA: List<Currency>,
        currenciesB: List<Currency>,
    ): List<Currency> {
        val currencies = (currenciesA + currenciesB).distinctBy { currency ->
            currency.currencyCode
        }
        currencies.onEach { currency ->
            currency.position = currenciesB.single { currencyB ->
                currencyB.currencyCode == currency.currencyCode
            }.position
        }
        return currencies.sortedBy { currency ->
            currency.position
        }
    }

    /**
     * Removes currencies from the in-memory list that are not present in the database list.
     *
     * @param currenciesA The list of in-memory currencies.
     * @param currenciesB The list of database currencies.
     * @return A new list of currencies that exist in both the in-memory and database lists, with positions updated from the database.
     */
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

    /**
     * Reorders the in-memory currencies to match the order of the database currencies.
     *
     * @param currenciesA The list of in-memory currencies.
     * @param currenciesB The list of database currencies.
     * @return A new list of in-memory currencies reordered to match the database list by position.
     */
    private fun reorderCurrencies(
        currenciesA: List<Currency>,
        currenciesB: List<Currency>,
    ): List<Currency> {
        return currenciesA.onEach { currencyA ->
            val correspondingCurrencyB = currenciesB.first { currencyB ->
                currencyB.currencyCode == currencyA.currencyCode
            }
            currencyA.position = correspondingCurrencyB.position
        }.sortedBy { currency ->
            currency.position
        }
    }
}
