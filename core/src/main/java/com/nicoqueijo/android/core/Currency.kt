package com.nicoqueijo.android.core

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.nicoqueijo.android.core.extensions.roundToFourDecimalPlaces
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

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
    private var decimalFormatter: DecimalFormat

    @Ignore
    private var decimalSeparator: String

    init {
        val numberFormatter = NumberFormat.getNumberInstance(Locale.getDefault())
        val conversionPattern = "#,##0.####"
        decimalFormatter = numberFormatter as DecimalFormat
        decimalFormatter.applyPattern(conversionPattern)
        decimalSeparator = decimalFormatter.decimalFormatSymbols.decimalSeparator.toString()
    }

    /**
     * Currency code without the "USD_" prefix.
     * Example: USD_EUR -> EUR
     */
    val trimmedCurrencyCode
        get() = currencyCode.substring(CURRENCY_CODE_START_INDEX)

    /**
     * TODO: Might not need this. This may be copy/paste code from the original project that was
     * done in Java and did not leverage data classes.
     */
    override fun equals(other: Any?): Boolean {
        return this === other
        /*if (this === other) return true*/
        if (javaClass != other?.javaClass) return false
        other as Currency
        return currencyCode == other.currencyCode
    }

    fun deepEquals(other: Currency): Boolean {
        return (this.currencyCode == other.currencyCode &&
                this.exchangeRate == other.exchangeRate &&
                this.isSelected == other.isSelected &&
                this.position == other.position)
    }

    fun deepCopy(): Currency {
        return this.copy().also { copy ->
            copy.isFocused = this.isFocused
            copy.conversion = Conversion(conversionValue = this.conversion.value).also { conversion ->
                conversion.value = this.conversion.value
                conversion.valueAsString = this.conversion.valueAsString
                /*conversion.hint = this.conversion.hint*/
            }
        }
    }

    /**
     * TODO: Might not need this. This may be copy/paste code from the original project that was
     * done in Java and did not leverage data classes.
     */
    override fun hashCode() = currencyCode.hashCode()

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

    inner class Conversion(conversionValue: BigDecimal) {
        /**
         * The underlying numeric conversion result.
         * Example: 1234.5678
         */
        var value: BigDecimal = conversionValue
            set(value) {
                field = value.roundToFourDecimalPlaces()
                valueAsString = field.toString()
            }

        /**
         * The [value] as a String.
         * Example: "1234.5678"
         */
        var valueAsString = ""

        /**
         * The [valueAsString] formatted according to locale.
         * Example:    USA: 1,234.5678
         *          France: 1.234,5678
         */
        val valueAsText: String
            get() {
                return if (valueAsString.isNotBlank()) {
                    formatConversion(valueAsString)
                } else {
                    ""
                }
            }

        /**
         * The hint displayed when [valueAsText] is empty.
         */
        var hint = ""
            set(value) {
                field = formatConversion(BigDecimal(value).toString())
            }

        /**
         * Formats a numeric String with grouping separators while retaining trailing zeros.
         */
        private fun formatConversion(conversion: String): String {
            return when {
                conversion.contains(".") -> {
                    val splitConversion = conversion.split(".")
                    val wholePart = splitConversion[Position.FIRST.value]
                    val decimalPart = splitConversion[Position.SECOND.value]
                    decimalFormatter.format(BigDecimal(wholePart)) + decimalSeparator + decimalPart
                }

                else -> {
                    decimalFormatter.format(BigDecimal(conversion))
                }
            }
        }
    }
}

enum class Position(val value: Int) {
    INVALID(-1),
    FIRST(0),
    SECOND(1),
    THIRD(2),
    FOURTH(3)
}

const val CURRENCY_CODE_START_INDEX = 4