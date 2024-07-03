package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.core.model.Position
import com.nicoqueijo.android.data.Repository
import kotlinx.coroutines.flow.first

class UnselectCurrencyUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke(currency: Currency) {
        val selectedCurrencies = repository.getSelectedCurrencies().first()
        val currencyToUnselect = selectedCurrencies.first { it.currencyCode == currency.currencyCode }


        for (i in selectedCurrencies.count() - 1 downTo  currencyToUnselect.position + 1) {
            selectedCurrencies[i].position = --selectedCurrencies[i].position
        }

        currencyToUnselect.apply {
            isSelected = false
            position = Position.INVALID.value
        }

        repository.upsertCurrencies(selectedCurrencies)


        /**
         * 1. Unselect the currency
         * 2. Change the position to invalid
         * 3. Adjust the positions of the currencies below this
         *      (decrement them by 1)
         */
    }
}