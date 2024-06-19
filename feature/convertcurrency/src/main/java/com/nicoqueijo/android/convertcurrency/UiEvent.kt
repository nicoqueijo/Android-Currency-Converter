package com.nicoqueijo.android.convertcurrency

sealed interface UiEvent {
    data object RemoveAllCurrencies : UiEvent
    data object ConfirmDialog : UiEvent
    data object CancelDialog : UiEvent
}