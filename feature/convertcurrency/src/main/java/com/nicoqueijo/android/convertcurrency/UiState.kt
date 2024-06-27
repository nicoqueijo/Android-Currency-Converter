package com.nicoqueijo.android.convertcurrency

import com.nicoqueijo.android.core.Currency

data class UiState(
    val selectedCurrencies: List<Currency> = emptyList(),
    /*val focusedCurrency: Currency? = null,*/
    /*val focusedCurrency: Currency? = selectedCurrencies.single { it.isFocused },*/
    val showDialog: Boolean = false,
)
