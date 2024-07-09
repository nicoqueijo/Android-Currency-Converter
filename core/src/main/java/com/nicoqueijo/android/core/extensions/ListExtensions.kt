package com.nicoqueijo.android.core.extensions

import com.nicoqueijo.android.core.model.Currency

/**
 * Extension function for [List] of [Currency] that checks for deep equality with another list.
 *
 * @param other The other list of [Currency] to compare with.
 * @return `true` if both lists are of the same size and all corresponding elements are equal, `false` otherwise.
 */
fun List<Currency>.deepEquals(other: List<Currency>): Boolean {
    if (this.size != other.size) {
        return false
    }
    for (i in indices) {
        val currencyA = this[i]
        val currencyB = other[i]
        if (currencyA != currencyB) {
            return false
        }
    }
    return true
}

/**
 * Extension function for [List] of [Currency] that creates a deep copy of the list.
 *
 * @return A new list of [Currency] where each element is a deep copy of the corresponding element in the original list.
 */
fun List<Currency>.deepCopy(): List<Currency> {
    return this.map { currency ->
        currency.deepCopy()
    }
}