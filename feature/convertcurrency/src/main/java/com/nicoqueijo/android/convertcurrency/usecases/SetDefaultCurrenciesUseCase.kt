package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.core.model.Position
import com.nicoqueijo.android.data.Repository
import java.util.Locale

/**
 * Use case class responsible for setting default currencies if it's the first launch of the application.
 *
 * @property repository The repository interface used for accessing currency data.
 */
class SetDefaultCurrenciesUseCase(
    private val repository: Repository,
) {

    /**
     * Sets default currencies if it's the application's first launch. Defaults to the user's local currency
     * (if available) and the United States Dollar (USD). Otherwise it sets the United States Dollar (USD)
     * and the next three most commonly used currencies: Euro (EUR), Japanese Yen (JPY), and British Pound (GBP).
     */
    suspend operator fun invoke() {
        if (repository.isFirstLaunch()) {
            val defaultCurrencies = mutableListOf<Currency>()
            val localCurrencyCode = "USD_${java.util.Currency.getInstance(Locale.getDefault()).currencyCode}"
            val localCurrency = repository.getCurrency(localCurrencyCode)
            defaultCurrencies.add(
                element = setDefaultCurrency(
                    currency = repository.getCurrency(currencyCode = "USD_USD"),
                    position = Position.FIRST
                )
            )
            if (localCurrencyCode == "USD_USD" || localCurrency == null) { // localCurrency can be null if no matching currency in the db
                addDefaultCurrencies(
                    defaultCurrencies = defaultCurrencies,
                    currencyCodes = listOf("USD_EUR", "USD_JPY", "USD_GBP"),
                    positions = listOf(Position.SECOND, Position.THIRD, Position.FOURTH)
                )
            } else {
                defaultCurrencies.add(
                    element = setDefaultCurrency(
                        currency = localCurrency,
                        position = Position.SECOND
                    )
                )
            }
            repository.upsertCurrencies(defaultCurrencies)
        }
    }

    /**
     * Adds default currencies to the list based on provided currency codes and positions.
     *
     * @param defaultCurrencies The list of default currencies to modify.
     * @param currencyCodes List of currency codes to retrieve currencies.
     * @param positions List of positions to assign to each currency.
     */
    private suspend fun addDefaultCurrencies(
        defaultCurrencies: MutableList<Currency>,
        currencyCodes: List<String>,
        positions: List<Position>
    ) {
        currencyCodes.zip(positions).forEach { (code, position) ->
            repository.getCurrency(code).apply {
                defaultCurrencies.add(
                    element = setDefaultCurrency(this, position)
                )
            }
        }
    }

    /**
     * Sets default properties for a given currency.
     *
     * @param currency The currency object to modify.
     * @param position The position to assign to the currency.
     * @return The modified currency object with default properties set.
     */
    private fun setDefaultCurrency(currency: Currency, position: Position): Currency {
        currency.position = position.value
        currency.isSelected = true
        return currency
    }
}