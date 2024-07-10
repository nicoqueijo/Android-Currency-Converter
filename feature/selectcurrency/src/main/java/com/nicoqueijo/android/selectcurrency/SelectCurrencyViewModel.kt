package com.nicoqueijo.android.selectcurrency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicoqueijo.android.core.di.DefaultDispatcher
import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.selectcurrency.model.UiEvent
import com.nicoqueijo.android.selectcurrency.model.UiState
import com.nicoqueijo.android.selectcurrency.usecases.SelectCurrencyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing the selection and filtering of currencies.
 *
 * This ViewModel handles various operations related to currency selection and filtering,
 * including initializing the list of available currencies and handling user events to
 * filter and select currencies.
 *
 * @property useCases The collection of use cases for currency selection and retrieval operations.
 * @property dispatcher The coroutine dispatcher for executing asynchronous tasks.
 */
@HiltViewModel
class SelectCurrencyViewModel @Inject constructor(
    private val useCases: SelectCurrencyUseCases,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow(value = UiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(context = dispatcher) {
            _uiState.value = _uiState.value.copy(
                filteredCurrencies = useCases.retrieveCurrenciesUseCase()
            )
        }
    }

    /**
     * Handles various UI events and delegates them to the appropriate use cases or functions.
     *
     * @param event The UI event to handle.
     */
    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.SearchTermChange -> {
                filterCurrencies(searchTerm = event.searchTerm)
            }

            is UiEvent.SelectCurrency -> {
                selectCurrency(selectedCurrency = event.currency)
            }
        }
    }

    private fun selectCurrency(selectedCurrency: Currency) {
        viewModelScope.launch(context = dispatcher) {
            useCases.selectCurrencyUseCase(selectedCurrency = selectedCurrency)
        }
    }

    private fun filterCurrencies(searchTerm: String) {
        _uiState.value = _uiState.value.copy(
            searchTerm = searchTerm,
        )
        viewModelScope.launch(context = dispatcher) {
            val filteredCurrencies = useCases.filterCurrenciesUseCase.invoke(
                searchTerm = searchTerm
            )
            _uiState.value = _uiState.value.copy(
                filteredCurrencies = filteredCurrencies,
                isSearchResultEmpty = filteredCurrencies.isEmpty() && searchTerm.isNotEmpty(),
            )
        }
    }
}
