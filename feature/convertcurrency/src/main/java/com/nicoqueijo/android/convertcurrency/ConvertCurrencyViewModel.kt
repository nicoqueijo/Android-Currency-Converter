package com.nicoqueijo.android.convertcurrency

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicoqueijo.android.convertcurrency.usecases.ConvertCurrencyUseCases
import com.nicoqueijo.android.convertcurrency.util.KeyboardInput
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
            useCases.retrieveSelectedCurrenciesUseCase.invoke()
                .collectLatest { selectedCurrenciesFromDatabase ->
                    updateSelectedCurrencies(
                        selectedCurrenciesFromMemory = uiState.value.selectedCurrencies,
                        selectedCurrenciesFromDatabase = selectedCurrenciesFromDatabase,
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

            is UiEvent.ProcessKeyboardInput -> {
                processKeyboardInput(keyboardInput = event.keyboardInput)
            }
        }
    }

    private fun setDefaultFocusedCurrency() {
        viewModelScope.launch(context = dispatcher) {
            _uiState.value = _uiState.value.copy(
                focusedCurrency = useCases.setDefaultFocusedCurrency.invoke(
                    focusedCurrency = uiState.value.focusedCurrency,
                    selectedCurrencies = uiState.value.selectedCurrencies
                )
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

    private fun updateFocusedCurrency(currencyToFocus: Currency) {
        viewModelScope.launch(context = dispatcher) {
            _uiState.value = _uiState.value.copy(
                focusedCurrency = useCases.updateFocusedCurrencyUseCase.invoke(
                    selectedCurrencies = uiState.value.selectedCurrencies,
                    currencyToFocus = currencyToFocus
                )
            )
        }
    }

    private fun updateSelectedCurrencies(
        selectedCurrenciesFromMemory: List<Currency>,
        selectedCurrenciesFromDatabase: List<Currency>,
    ) {
        viewModelScope.launch(context = dispatcher) {
            _uiState.value = _uiState.value.copy(
                selectedCurrencies = useCases.updateSelectedCurrenciesUseCase.invoke(
                    selectedCurrenciesFromMemory = selectedCurrenciesFromMemory,
                    selectedCurrenciesFromDatabase = selectedCurrenciesFromDatabase,
                )
            )
        }
    }

    private fun processKeyboardInput(keyboardInput: KeyboardInput) {
        useCases.processKeyboardInputUseCase(
            keyboardInput = keyboardInput,
            focusedCurrency = uiState.value.focusedCurrency,
            selectedCurrencies = uiState.value.selectedCurrencies
        )
    }
}
