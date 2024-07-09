package com.nicoqueijo.android.core.model

import com.nicoqueijo.android.core.extensions.roundToFourDecimalPlaces
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

/**
 * Class representing a currency conversion with formatting capabilities.
 *
 * @param conversionValue The initial conversion value as a [BigDecimal].
 * @property value The conversion value, rounded to four decimal places.
 * Example: 1234.5678
 * @property valueAsString The [value] as a string.
 * Example: "1234.5678"
 * @property valueAsText The [valueAsString] formatted according to locale.
 * Example: USA: 1,234.5678, France: 1.234,5678
 * @property hint The hint displayed when [valueAsText] is empty.
 */
class Conversion(conversionValue: BigDecimal) {

    private var decimalFormatter: DecimalFormat
    private var decimalSeparator: String

    init {
        val numberFormatter = NumberFormat.getNumberInstance(Locale.getDefault())
        val conversionPattern = "#,##0.####"
        decimalFormatter = numberFormatter as DecimalFormat
        decimalFormatter.applyPattern(conversionPattern)
        decimalSeparator = decimalFormatter.decimalFormatSymbols.decimalSeparator.toString()
    }

    var value: BigDecimal = conversionValue
        set(value) {
            field = value.roundToFourDecimalPlaces()
            valueAsString = field.toString()
        }

    var valueAsString: String = ""

    val valueAsText: String
        get() {
            return if (valueAsString.isNotBlank()) {
                formatConversion(valueAsString)
            } else {
                ""
            }
        }

    var hint: Hint = Hint()
        set(value) {
            val formattedNumber = formatConversion(
                number = BigDecimal(value.number).toString()
            )
            field = Hint(
                number = value.number,
                formattedNumber = formattedNumber
            )
        }

    /**
     * Formats a numeric String with grouping separators while retaining trailing zeros.
     *
     * @param number The number to be formatted as a string.
     * @return The formatted number as a string.
     */
    private fun formatConversion(number: String): String {
        return when {
            number.contains(".") -> {
                val splitConversion = number.split(".")
                val wholePart = splitConversion[Position.FIRST.value]
                val decimalPart = splitConversion[Position.SECOND.value]
                decimalFormatter.format(BigDecimal(wholePart)) + decimalSeparator + decimalPart
            }

            else -> {
                decimalFormatter.format(BigDecimal(number))
            }
        }
    }
}