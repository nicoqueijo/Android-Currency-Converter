package com.nicoqueijo.android.selectcurrency

import com.nicoqueijo.android.core.Currency

sealed interface UiEvent {
    data class SelectCurrency(val currency: Currency): UiEvent
    data class SearchTermChange(val searchTerm: String) : UiEvent
}