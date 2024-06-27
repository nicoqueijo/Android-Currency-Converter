package com.nicoqueijo.android.core

import com.nicoqueijo.android.core.extensions.roundToFourDecimalPlaces
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

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
    var hint = "1"
        set(value) {
            field = formatConversion(
                conversion = BigDecimal(value).toString()
            )
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