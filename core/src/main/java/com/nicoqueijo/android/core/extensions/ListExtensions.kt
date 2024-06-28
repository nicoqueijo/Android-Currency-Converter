package com.nicoqueijo.android.core.extensions

import com.nicoqueijo.android.core.model.Currency

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

fun List<Currency>.deepCopy(): List<Currency> {
    return this.map { currency ->
        currency.deepCopy()
    }
}