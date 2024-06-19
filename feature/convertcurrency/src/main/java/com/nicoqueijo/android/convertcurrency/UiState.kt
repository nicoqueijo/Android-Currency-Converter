package com.nicoqueijo.android.convertcurrency

import com.nicoqueijo.android.core.Currency

data class UiState(
    val selectedCurrencies: List<Currency> = emptyList(),
    val focusedCurrency: Currency? = null, // unused for now
    val showDialog: Boolean = false,
)
