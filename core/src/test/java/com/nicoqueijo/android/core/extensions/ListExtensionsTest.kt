package com.nicoqueijo.android.core.extensions

import com.nicoqueijo.android.core.model.Conversion
import com.nicoqueijo.android.core.model.Currency
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ListExtensionsTest {

    @Nested
    inner class DeepCopy {

        @Test
        fun `given a list of Currency objects, when deepCopy is called, then it should create a new instance`() {
            val subject = listOf(
                Currency(
                    currencyCode = "USD_EUR",
                    exchangeRate = 1.12,
                    position = 2,
                    isSelected = true,
                ),
                Currency(
                    currencyCode = "USD_CAD",
                    exchangeRate = 0.73,
                    position = 3,
                    isSelected = true,
                )
            )

            val copy = subject.deepCopy()

            subject.shouldNotBeSameInstanceAs(copy)
            subject.zip(copy).forEach { pair ->
                val subjectItem = pair.first
                val copyItem = pair.second
                subjectItem.shouldNotBeSameInstanceAs(copyItem)
            }
        }

        @Test
        fun `given a list of Currency objects, when deepCopy is called, then the copied object should have the same property values`() {

            val subject = listOf(
                Currency(
                    currencyCode = "USD_EUR",
                    exchangeRate = 1.12,
                    position = 2,
                    isSelected = true,
                ).apply {
                    conversion = Conversion(
                        conversionValue = BigDecimal("2.2400")
                    )
                },
                Currency(
                    currencyCode = "USD_CAD",
                    exchangeRate = 0.73,
                    position = 3,
                    isSelected = true,
                ).apply {
                    conversion = Conversion(
                        conversionValue = BigDecimal("1.5844")
                    )
                }
            )

            val copy = subject.deepCopy()

            subject.zip(copy).forEach { pair ->
                val subjectItem = pair.first
                val copyItem = pair.second
                subjectItem.apply {
                    currencyCode.shouldBe(copyItem.currencyCode)
                    exchangeRate.shouldBe(copyItem.exchangeRate)
                    position.shouldBe(copyItem.position)
                    isSelected.shouldBe(copyItem.isSelected)
                    isFocused.shouldBe(copyItem.isFocused)
                    isInputValid.shouldBe(copyItem.isInputValid)
                    conversion.value.shouldBe(copyItem.conversion.value)
                    conversion.valueAsString.shouldBe(copyItem.conversion.valueAsString)
                    conversion.hint.shouldBe(copyItem.conversion.hint)
                }
            }
        }
    }
}