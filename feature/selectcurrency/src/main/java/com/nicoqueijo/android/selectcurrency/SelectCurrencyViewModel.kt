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
        viewModelScope.launch(context = dispatcher) {
            _uiState.value = _uiState.value.copy(
                searchTerm = searchTerm,
            )
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
