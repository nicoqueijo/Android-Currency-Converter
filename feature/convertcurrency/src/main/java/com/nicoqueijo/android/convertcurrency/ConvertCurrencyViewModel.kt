package com.nicoqueijo.android.convertcurrency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicoqueijo.android.convertcurrency.usecases.ConvertCurrencyUseCases
import com.nicoqueijo.android.core.di.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            _uiState.value = _uiState.value.copy(
                selectedCurrencies = useCases.retrieveSelectedCurrenciesUseCase()
            )

            /**
             * This makes the first currency in the list the focused currency but fails to retain
             * that info as when we come back from the Selector screen we update the state with the
             * db currencies which doesn't have info on focus. We can make the db store the focus
             * but we'll still have the same issue for conversions (they conversions in memory are
             * erased and we can't store all that info in the db). What we can do is just pop the
             * backstack but at the time we are not listening for new selected currencies from the db.
             */
            if (_uiState.value.focusedCurrency == null && _uiState.value.selectedCurrencies.isNotEmpty()) {
                _uiState.value = _uiState.value.copy(
                    focusedCurrency = _uiState.value.selectedCurrencies.take(1).single().also { firstCurrency ->
                        firstCurrency.isFocused = true
                    }
                )
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
                _uiState.value.selectedCurrencies.first { it.isFocused }.isFocused = false
                _uiState.value.selectedCurrencies.first { it == event.currency }.isFocused = true
                _uiState.value = _uiState.value.copy(
                  focusedCurrency = event.currency
                )
            }
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
                selectedCurrencies = useCases.retrieveSelectedCurrenciesUseCase()
            )
        }
    }
}
