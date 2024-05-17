package com.nicoqueijo.android.currencyconverter.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nicoqueijo.android.core.di.DefaultDispatcher
import com.nicoqueijo.android.data.Repository
import com.nicoqueijo.android.network.ExchangeRates
import com.nicoqueijo.android.network.OpenExchangeRatesEndPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: Repository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow(value = SplashUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun fetchCurrencies() {
        withContext(context = dispatcher) {
            if (repository.isDataEmpty() || repository.isDataStale()) {
                repository.getExchangeRates().onSuccess { data ->
                    persistResponse(payload = data)
                    _uiState.value = uiState.value.copy(isDataRetrievalSuccessful = true)
                }.onFailure { exception ->
                    _uiState.value = uiState.value.copy(isDataRetrievalSuccessful = false)
                    exception.message?.let { errorMessage ->
                        Log.e(
                            SplashViewModel::class.simpleName,
                            errorMessage
                        )
                    }
                }
            } else if (!repository.isDataEmpty()) {
                _uiState.value = uiState.value.copy(isDataRetrievalSuccessful = true)
            } else {
                _uiState.value = uiState.value.copy(isDataRetrievalSuccessful = false)
            }
        }
    }

    private suspend fun persistResponse(payload: OpenExchangeRatesEndPoint) {
        withContext(context = dispatcher) {
            payload.exchangeRates?.let { exchangeRates ->
                persistCurrencies(exchangeRates = exchangeRates)
            }
            persistTimestamp(timestamp = payload.timestamp)
        }
    }

    private suspend fun persistCurrencies(exchangeRates: ExchangeRates) {
        withContext(context = dispatcher) {
            when {
                repository.isDataEmpty() -> {
                    repository.upsertCurrencies(currencies = exchangeRates.currencies)
                }

                repository.isDataStale() -> {
                    repository.updateExchangeRates(currencies = exchangeRates.currencies)
                }
            }
        }
    }

    private suspend fun persistTimestamp(timestamp: Long) {
        withContext(context = dispatcher) {
            repository.setTimestampInSeconds(value = timestamp)
        }
    }
}

data class SplashUiState(
    val isDataRetrievalSuccessful: Boolean? = null
)