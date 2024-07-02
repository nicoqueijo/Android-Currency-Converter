package com.nicoqueijo.android.selectcurrency.model

import com.nicoqueijo.android.core.model.Currency

sealed interface UiEvent {
    data class SelectCurrency(val currency: Currency) : UiEvent
    data class SearchTermChange(val searchTerm: String) : UiEvent
}