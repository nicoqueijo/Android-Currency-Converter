package com.nicoqueijo.android.selectcurrency.model

import com.nicoqueijo.android.core.model.Currency

/**
 * Data class representing the state of the user interface in the currency conversion application.
 *
 * @property filteredCurrencies The list of currencies filtered based on the search term.
 * @property searchTerm The current search term entered by the user.
 * @property isSearchResultEmpty A boolean flag indicating whether the search result is empty.
 */
data class UiState(
    val filteredCurrencies: List<Currency> = emptyList(),
    val searchTerm: String = "",
    val isSearchResultEmpty: Boolean = false,
)
