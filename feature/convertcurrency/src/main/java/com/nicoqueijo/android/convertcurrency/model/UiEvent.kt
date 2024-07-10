package com.nicoqueijo.android.convertcurrency.model

import com.nicoqueijo.android.convertcurrency.util.KeyboardInput
import com.nicoqueijo.android.core.model.Currency

/**
 * Sealed interface representing events that drive changes in the user interface.
 * Implementing classes or objects of [UiEvent] encapsulate specific actions or intents.
 */
sealed interface UiEvent {
    data object UnselectAllCurrencies : UiEvent
    data class UnselectCurrency(val currency: Currency) : UiEvent
    data class RestoreCurrency(val currency: Currency) : UiEvent
    data object ConfirmDialog : UiEvent
    data object CancelDialog : UiEvent
    data class SetCurrencyFocus(val currency: Currency) : UiEvent
    data class ProcessKeyboardInput(val keyboardInput: KeyboardInput) : UiEvent
    data class ReorderCurrencies(val currencies: List<Currency>) : UiEvent
    data object ToggleOffIsFirstLaunch : UiEvent
}
