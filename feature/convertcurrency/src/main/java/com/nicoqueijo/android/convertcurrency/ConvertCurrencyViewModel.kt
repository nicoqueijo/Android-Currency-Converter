package com.nicoqueijo.android.convertcurrency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicoqueijo.android.convertcurrency.model.UiEvent
import com.nicoqueijo.android.convertcurrency.model.UiState
import com.nicoqueijo.android.convertcurrency.usecases.ConvertCurrencyUseCases
import com.nicoqueijo.android.convertcurrency.util.KeyboardInput
import com.nicoqueijo.android.core.di.DefaultDispatcher
import com.nicoqueijo.android.core.model.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
            useCases.retrieveSelectedCurrenciesUseCase()
                .collectLatest { databaseCurrencies ->
                    updateSelectedCurrencies(
                        memoryCurrencies = _uiState.value.currencies,
                        databaseCurrencies = databaseCurrencies,
                    )
                    setDefaultFocusedCurrency()
                    updateHints()
                    updateConversions()
                }
        }
    }

    fun onEvent(event: UiEvent) {
        when (event) {
            UiEvent.UnselectAllCurrencies -> {
                updateDialogDisplay(toggle = true)
            }

            is UiEvent.UnselectCurrency -> {
                unselectCurrency(currency = event.currency)
            }

            is UiEvent.RestoreCurrency -> {
                restoreCurrency(currency = event.currency)
            }

            UiEvent.ConfirmDialog -> {
                unselectAllCurrencies()
                updateDialogDisplay(toggle = false)
            }

            UiEvent.CancelDialog -> {
                updateDialogDisplay(toggle = false)
            }

            is UiEvent.SetCurrencyFocus -> {
                updateFocusedCurrency(currencyToFocus = event.currency)
                updateHints()
            }

            is UiEvent.ProcessKeyboardInput -> {
                processKeyboardInput(keyboardInput = event.keyboardInput)
            }

            is UiEvent.ReorderCurrencies -> {
                reorderCurrencies(currencies = event.currencies)
            }
        }
    }

    private fun setDefaultFocusedCurrency() {
        val updatedCurrencies = useCases.setDefaultFocusedCurrency(
            currencies = _uiState.value.currencies
        )
        _uiState.value = _uiState.value.copy(
            currencies = updatedCurrencies
        )
    }

    private fun updateDialogDisplay(toggle: Boolean) {
        viewModelScope.launch(context = dispatcher) {
            _uiState.value = _uiState.value.copy(
                showDialog = toggle
            )
        }
    }

    private fun unselectAllCurrencies() {
        viewModelScope.launch(context = dispatcher) {
            useCases.unselectAllCurrenciesUseCase()
        }
    }

    private fun unselectCurrency(currency: Currency) {
        viewModelScope.launch(context = dispatcher) {
            useCases.unselectCurrencyUseCase(currency = currency)
        }
    }

    private fun restoreCurrency(currency: Currency) {
        viewModelScope.launch(context = dispatcher) {
            useCases.restoreCurrencyUseCase(currency = currency)
        }
    }

    private fun updateFocusedCurrency(currencyToFocus: Currency) {
        val updatedCurrencies = useCases.updateFocusedCurrencyUseCase(
            currencies = _uiState.value.currencies,
            currencyToFocus = currencyToFocus,
        )
        _uiState.value = _uiState.value.copy(
            currencies = updatedCurrencies
        )
    }

    private fun updateSelectedCurrencies(
        memoryCurrencies: List<Currency>,
        databaseCurrencies: List<Currency>,
    ) {
        val updatedSelectedCurrencies = useCases.updateSelectedCurrenciesUseCase.invoke(
            memoryCurrencies = memoryCurrencies,
            databaseCurrencies = databaseCurrencies,
        )
        _uiState.value = _uiState.value.copy(
            currencies = updatedSelectedCurrencies
        )
    }

    private fun processKeyboardInput(keyboardInput: KeyboardInput) {
        val inputResult = useCases.processKeyboardInputUseCase(
            keyboardInput = keyboardInput,
            currencies = _uiState.value.currencies,
        )
        _uiState.value = _uiState.value.copy(
            currencies = inputResult.currencies,
        )
        if (inputResult.isInputValid) {
            updateConversions()
        }
    }

    private fun updateHints() {
        val updatedCurrencies = useCases.updateHintsUseCase(
            currencies = _uiState.value.currencies
        )
        _uiState.value = _uiState.value.copy(
            currencies = updatedCurrencies
        )
    }

    private fun updateConversions() {
        val updatedCurrencies = useCases.updateConversionsUseCase(
            currencies = _uiState.value.currencies
        )
        _uiState.value = _uiState.value.copy(
            currencies = updatedCurrencies
        )
    }

    private fun reorderCurrencies(currencies: List<Currency>) {
        viewModelScope.launch(context = dispatcher) {
            useCases.reorderCurrenciesUseCase(currencies = currencies)
        }
    }
}
