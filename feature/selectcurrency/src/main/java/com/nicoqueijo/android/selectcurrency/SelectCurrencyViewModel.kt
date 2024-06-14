package com.nicoqueijo.android.selectcurrency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.core.di.DefaultDispatcher
import com.nicoqueijo.android.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectCurrencyViewModel @Inject constructor(
    private val repository: Repository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow(value = SelectCurrencyUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(context = dispatcher) {
            _uiState.value = _uiState.value.copy(
                filteredCurrencies = repository.getAllCurrencies()
            )
        }
    }

    fun handleCurrencySelection(selectedCurrency: Currency) {
        viewModelScope.launch(context = dispatcher) {
            val lastPosition = repository.getSelectedCurrencyCount()
            selectedCurrency.apply {
                isSelected = true
                position = lastPosition
            }
            repository.upsertCurrency(selectedCurrency)
        }
    }
}

data class SelectCurrencyUiState(
    val filteredCurrencies: List<Currency> = emptyList(),
)
