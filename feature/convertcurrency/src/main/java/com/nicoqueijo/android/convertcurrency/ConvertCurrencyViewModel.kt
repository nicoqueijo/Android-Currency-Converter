package com.nicoqueijo.android.convertcurrency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicoqueijo.android.convertcurrency.usecases.ConvertCurrencyUseCases
import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.core.di.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConvertCurrencyViewModel @Inject constructor(
    private val useCases: ConvertCurrencyUseCases,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow(value = UiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(context = dispatcher) {
            /**
             * TODO: Move logic to a use case
             * Listens to changes in the selected currencies from the db - like when the user selects
             * a new currency, the selected currencies in the db changed, and the SelectCurrency screen
             * is popped off. Will also be called when the app launches and the list is not empty.
             */
            useCases.retrieveSelectedCurrenciesUseCase.invoke().collectLatest { dbCurrencies ->
                val memoryCurrencies = _uiState.value.selectedCurrencies
                val uniques = dbCurrencies.filter { it !in memoryCurrencies }
                val mergedList = memoryCurrencies + uniques
                _uiState.value = _uiState.value.copy(
                    selectedCurrencies = mergedList
                )
                setDefaultFocusedCurrency()
            }
        }
    }

    fun onEvent(event: UiEvent) {
        when (event) {
            UiEvent.RemoveAllCurrencies -> {
                updateDialogDisplay(toggle = true)
            }

            UiEvent.ConfirmDialog -> {
                removeSelectedCurrencies()
                updateDialogDisplay(toggle = false)
            }

            UiEvent.CancelDialog -> {
                updateDialogDisplay(toggle = false)
            }

            is UiEvent.SetCurrencyFocus -> {
                updateFocusedCurrency(currencyToFocus = event.currency)
            }
        }
    }

    /**
     * TODO: Move logic to use case
     */
    private fun setDefaultFocusedCurrency() {
        if (_uiState.value.focusedCurrency == null && _uiState.value.selectedCurrencies.isNotEmpty()) {
            _uiState.value = _uiState.value.copy(
                focusedCurrency = _uiState.value.selectedCurrencies.take(1).single()
                    .also { firstCurrency ->
                        firstCurrency.isFocused = true
                    }
            )
        }
    }

    private fun updateDialogDisplay(toggle: Boolean) {
        viewModelScope.launch(context = dispatcher) {
            _uiState.value = _uiState.value.copy(
                showDialog = toggle
            )
        }
    }

    private fun removeSelectedCurrencies() {
        viewModelScope.launch(context = dispatcher) {
            useCases.removeSelectedCurrenciesUseCase()
            _uiState.value = _uiState.value.copy(
                selectedCurrencies = useCases.retrieveSelectedCurrenciesUseCase().first(),
                focusedCurrency = null,
            )
        }
    }

    /**
     * TODO: Move logic to use case
     */
    private fun updateFocusedCurrency(currencyToFocus: Currency) {
        _uiState.value.selectedCurrencies.first { it.isFocused }.isFocused = false
        _uiState.value.selectedCurrencies.first { it == currencyToFocus }.isFocused = true
        _uiState.value = _uiState.value.copy(
            focusedCurrency = currencyToFocus
        )
    }
}
