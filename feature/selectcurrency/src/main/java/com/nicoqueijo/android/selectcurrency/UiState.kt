package com.nicoqueijo.android.selectcurrency

import com.nicoqueijo.android.core.model.Currency

data class UiState(
    val filteredCurrencies: List<Currency> = emptyList(),
    val searchTerm: String = "",
    val isSearchResultEmpty: Boolean = false,
)
