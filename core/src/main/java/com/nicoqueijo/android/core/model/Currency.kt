package com.nicoqueijo.android.core.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.math.BigDecimal

/**
 * Represents a currency with its code and exchange rate.
 *
 * @property currencyCode The three-letter ISO 4217 currency code.
 * @property exchangeRate The exchange rate of this currency to one US Dollar (USD).
 */
@Entity
data class Currency(
    @PrimaryKey
    val currencyCode: String,
    val exchangeRate: Double,
    var position: Int = Position.INVALID.value,
    var isSelected: Boolean = false,
) {

    @Ignore
    var isFocused = false

    @Ignore
    var conversion = Conversion(BigDecimal.ZERO)

    @Ignore
    var isInputValid = true

    /**
     * Currency code without the "USD_" prefix.
     * Example: USD_EUR -> EUR
     */
    val trimmedCurrencyCode
        get() = currencyCode.substring(startIndex = 4)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Currency
        return this.deepEquals(other)
    }

    private fun deepEquals(other: Currency): Boolean {
        return this.currencyCode == other.currencyCode &&
                this.exchangeRate == other.exchangeRate &&
                this.position == other.position &&
                this.isSelected == other.isSelected &&
                this.isFocused == other.isFocused &&
                this.isInputValid == other.isInputValid &&
                this.conversion.value == other.conversion.value &&
                this.conversion.valueAsString == other.conversion.valueAsString &&
                this.conversion.hint == other.conversion.hint
    }

    fun deepCopy(): Currency {
        return this.copy().also { copy ->
            copy.isFocused = this.isFocused
            copy.isInputValid = this.isInputValid
            copy.conversion =
                Conversion(conversionValue = this.conversion.value).also { conversion ->
                    conversion.value = this.conversion.value
                    conversion.valueAsString = this.conversion.valueAsString
                    conversion.hint = this.conversion.hint.copy()
                }
        }
    }

    override fun hashCode(): Int {
        var result = currencyCode.hashCode()
        result = 31 * result + exchangeRate.hashCode()
        result = 31 * result + position
        result = 31 * result + isSelected.hashCode()
        result = 31 * result + isFocused.hashCode()
        result = 31 * result + isInputValid.hashCode()
        result = 31 * result + conversion.value.hashCode()
        result = 31 * result + conversion.valueAsString.hashCode()
        result = 31 * result + conversion.hint.hashCode()
        return result
    }

    /**
     * Since the toString() method is really only useful for debugging I've structured it in a way
     * which concisely displays the object's state.
     *
     * Example: 4 S* F* EUR
     *          | |  |   |
     *      Order |  |   |
     *     Selected? |   |
     *         Focused?  |
     *            Currency code
     *
     *    *blank if not selected/focused
     */
    override fun toString() = buildString {
        append("{")
        append(position)
        append(" ")
        append(trimmedCurrencyCode)
        append(" ")
        append(if (isFocused) "F" else " ")
        append(" ")
        append(if (isSelected) "S" else " ")
        append("}")
    }
}

enum class Position(val value: Int) {
    INVALID(-1),
    FIRST(0),
    SECOND(1),
    THIRD(2),
    FOURTH(3)
}
