package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.core.model.Position
import com.nicoqueijo.android.data.Repository
import java.util.Locale

class SetDefaultCurrenciesUseCase(
    private val repository: Repository,
) {

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

    private fun setDefaultCurrency(currency: Currency, position: Position): Currency {
        currency.position = position.value
        currency.isSelected = true
        return currency
    }
}