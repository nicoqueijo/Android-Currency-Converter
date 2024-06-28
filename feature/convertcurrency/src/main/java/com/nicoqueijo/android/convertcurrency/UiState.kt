package com.nicoqueijo.android.convertcurrency

import com.nicoqueijo.android.core.Currency

data class UiState(
    val currencies: List<Currency> = emptyList(),
    val showDialog: Boolean = false,
)
