package com.nicoqueijo.android.convertcurrency

import com.nicoqueijo.android.convertcurrency.util.KeyboardInput
import com.nicoqueijo.android.core.Currency

sealed interface UiEvent {
    data object RemoveAllCurrencies : UiEvent
    data object ConfirmDialog : UiEvent
    data object CancelDialog : UiEvent
    data class SetCurrencyFocus(val currency: Currency) : UiEvent
    data class ProcessKeyboardInput(val keyboardInput: KeyboardInput): UiEvent
}