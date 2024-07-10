package com.nicoqueijo.android.convertcurrency.model

import com.nicoqueijo.android.core.model.Currency

/**
 * Data class representing the current state of the user interface.
 *
 * @property currencies A list of [Currency] objects representing the currencies displayed in the UI.
 * @property showDialog A boolean indicating whether a dialog should be shown or not.
 * @property isFirstLaunch A boolean flag to determine if the current session is the first launch of the application.
 */
data class UiState(
    val currencies: List<Currency> = emptyList(),
    val showDialog: Boolean = false,
    val isFirstLaunch: Boolean = true,
)
