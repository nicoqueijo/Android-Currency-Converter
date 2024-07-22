package com.nicoqueijo.android.currencyconverter.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nicoqueijo.android.core.di.DefaultDispatcher
import com.nicoqueijo.android.data.Repository
import com.nicoqueijo.android.network.model.ExchangeRates
import com.nicoqueijo.android.network.model.OpenExchangeRatesEndPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel for the Splash screen that handles fetching currency data and updating the UI state
 * based on the success or failure of data retrieval.
 *
 * @property repository The repository for accessing data.
 * @property dispatcher The coroutine dispatcher for background tasks.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: Repository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow(value = SplashUiState())
    val uiState = _uiState.asStateFlow()

    /**
     * Fetches the currency data from the repository. This function checks if the data is either empty or stale.
     * If so, it retrieves the exchange rates from the repository and updates the UI state based on the success
     * or failure of this data retrieval. It logs any errors encountered during the process.
     *
     * - If the data is successfully retrieved and persisted, the UI state is updated to indicate success.
     * - If there is a failure in retrieving the data, the UI state is updated to indicate failure and the error
     *   message is logged.
     * - If the data is not empty and not stale, the UI state is updated to indicate that data retrieval is successful.
     *
     * @throws Exception If there is an error during the data retrieval process.
     */
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

    /**
     * Persists the response payload containing exchange rates and timestamp.
     *
     * @param payload The data payload containing exchange rates and timestamp.
     */
    private suspend fun persistResponse(payload: OpenExchangeRatesEndPoint) {
        withContext(context = dispatcher) {
            payload.exchangeRates?.let { exchangeRates ->
                persistCurrencies(exchangeRates = exchangeRates)
            }
            persistTimestamp(timestamp = payload.timestamp)
        }
    }

    /**
     * Persists the exchange rates by either inserting new data or updating existing data in the repository.
     *
     * @param exchangeRates The exchange rates to be persisted.
     */
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

    /**
     * Persists the timestamp in the repository.
     *
     * @param timestamp The timestamp to be persisted.
     */
    private suspend fun persistTimestamp(timestamp: Long) {
        withContext(context = dispatcher) {
            repository.setTimestampInSeconds(value = timestamp)
        }
    }
}

/**
 * A data class representing the UI state of the Splash screen.
 *
 * @property isDataRetrievalSuccessful A Boolean indicating whether data retrieval was successful.
 * It is null by default, indicating that the retrieval process has not yet been completed.
 */
data class SplashUiState(
    val isDataRetrievalSuccessful: Boolean? = null
)