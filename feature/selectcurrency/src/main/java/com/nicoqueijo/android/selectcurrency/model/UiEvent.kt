package com.nicoqueijo.android.selectcurrency.model

import com.nicoqueijo.android.core.model.Currency

/**
 * Sealed interface representing various user interface events in the currency conversion application.
 * Implementing classes or objects of [UiEvent] encapsulate specific user actions or intents.
 */
sealed interface UiEvent {
    data class SelectCurrency(val currency: Currency) : UiEvent
    data class SearchTermChange(val searchTerm: String) : UiEvent
}