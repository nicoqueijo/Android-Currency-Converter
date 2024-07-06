package com.nicoqueijo.android.convertcurrency.model

import com.nicoqueijo.android.convertcurrency.util.KeyboardInput
import com.nicoqueijo.android.core.model.Currency

sealed interface UiEvent {
    data object UnselectAllCurrencies : UiEvent
    data class UnselectCurrency(val currency: Currency) : UiEvent
    data class RestoreCurrency(val currency: Currency) : UiEvent
    data object ConfirmDialog : UiEvent
    data object CancelDialog : UiEvent
    data class SetCurrencyFocus(val currency: Currency) : UiEvent
    data class ProcessKeyboardInput(val keyboardInput: KeyboardInput) : UiEvent
    data class ReorderCurrencies(val currencies: List<Currency>) : UiEvent
}