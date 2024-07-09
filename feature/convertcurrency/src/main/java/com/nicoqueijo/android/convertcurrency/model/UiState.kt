package com.nicoqueijo.android.convertcurrency.model

import com.nicoqueijo.android.core.model.Currency

data class UiState(
    val currencies: List<Currency> = emptyList(),
    val showDialog: Boolean = false,
    val isFirstLaunch: Boolean = true,
)
