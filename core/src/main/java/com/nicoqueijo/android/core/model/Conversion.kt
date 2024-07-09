package com.nicoqueijo.android.core.model

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