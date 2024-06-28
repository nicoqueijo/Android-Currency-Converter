package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.data.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveSelectedCurrenciesUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(): Flow<List<Currency>> {
        return repository.getSelectedCurrencies()
    }
}