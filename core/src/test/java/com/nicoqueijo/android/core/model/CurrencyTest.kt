package com.nicoqueijo.android.core.model

import io.kotest.matchers.bigdecimal.shouldBeZero
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class CurrencyTest {

    @Nested
    inner class InitialState {

        @Test
        fun `given a new Currency object, when initialized as the Euro, then it should have default values`() {
            val subject = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12,
            )

            subject.apply {
                currencyCode.shouldBe("USD_EUR")
                exchangeRate.shouldBe(1.12)
                position.shouldBe(Position.INVALID.value)
                isSelected.shouldBeFalse()
                isFocused.shouldBeFalse()
                conversion.value.shouldBeZero()
                conversion.valueAsString.shouldBeEmpty()
                conversion.hint.number.shouldBe("1")
                conversion.hint.formattedNumber.shouldBe("1")
                isInputValid.shouldBeTrue()
            }
        }

        @Test
        fun `given a new Currency object, when initialized as the Argentine Peso, then it should have default values`() {
            val subject = Currency(
                currencyCode = "USD_ARS",
                exchangeRate = 924.7415,
            )

            subject.apply {
                currencyCode.shouldBe("USD_ARS")
                exchangeRate.shouldBe(924.7415)
                position.shouldBe(Position.INVALID.value)
                isSelected.shouldBeFalse()
                isFocused.shouldBeFalse()
                conversion.value.shouldBeZero()
                conversion.valueAsString.shouldBeEmpty()
                conversion.hint.number.shouldBe("1")
                conversion.hint.formattedNumber.shouldBe("1")
                isInputValid.shouldBeTrue()
            }
        }
    }

    @Nested
    inner class TrimmedCurrencyCode {

        @Test
        fun `given a new Currency object, when initialized as the Euro, then it should return the correct trimmedCurrencyCode`() {
            val subject = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12,
            )

            subject.trimmedCurrencyCode.shouldBe("EUR")
        }

        @Test
        fun `given a new Currency object, when initialized as the Argentine Peso, then it should return the correct trimmedCurrencyCode`() {
            val subject = Currency(
                currencyCode = "USD_ARS",
                exchangeRate = 924.7415,
            )

            subject.trimmedCurrencyCode.shouldBe("ARS")
        }
    }

    @Nested
    inner class Equals {

        @Test
        fun `given two Currency objects with the same reference, when compared, then equals should return true`() {
            val subject1 = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12,
            )

            val subject2 = subject1

            subject1.equals(subject2).shouldBeTrue()
        }

        @Test
        fun `given a Currency object and an object of different type, when compared, then equals should return false`() {
            val subject1 = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12,
            )

            val subject2 = Conversion(
                conversionValue = BigDecimal.ZERO
            )

            subject1.equals(subject2).shouldBeFalse()
        }

        @Test
        fun `given a Currency object and null, when compared, then equals should return false`() {
            val subject1 = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12,
            )

            val subject2: Currency? = null

            subject1.equals(subject2).shouldBeFalse()
        }

        @Test
        fun `given two identical Currency objects, when compared, then equals should return true`() {
            val subject1 = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12,
                position = 10,
                isSelected = true,
            ).apply {
                conversion = Conversion(
                    conversionValue = BigDecimal("2.24")
                )
            }
            val subject2 = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12,
                position = 10,
                isSelected = true,
            ).apply {
                conversion = Conversion(
                    conversionValue = BigDecimal("2.24")
                )
            }

            subject1.equals(subject2).shouldBeTrue()
        }

        @Test
        fun `given two Currency objects with different properties, when compared, then equals should return false`() {
            val subject1 = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12,
                position = 10,
                isSelected = true,
            ).apply {
                conversion = Conversion(
                    conversionValue = BigDecimal("2.24")
                )
            }
            val subject2 = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12,
                position = 6,
                isSelected = true,
            ).apply {
                conversion = Conversion(
                    conversionValue = BigDecimal("3.68")
                )
            }

            subject1.equals(subject2).shouldBeFalse()
        }
    }

    @Nested
    inner class HashCode {

        @Test
        fun `given a Currency object, when hashCode is called multiple times, then it should return the same value`() {
            val currency = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12,
                position = 10,
                isSelected = true,
            ).apply {
                conversion = Conversion(
                    conversionValue = BigDecimal("2.24")
                )
            }

            val subject1 = currency.hashCode()
            val subject2 = currency.hashCode()

            subject1.shouldBe(subject2)
        }

        @Test
        fun `given two equal Currency objects, when hashCode is called, then it should return the same value`() {
            val currency1 = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12,
                position = 10,
                isSelected = true,
            ).apply {
                conversion = Conversion(
                    conversionValue = BigDecimal("2.24")
                )
            }
            val currency2 = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12,
                position = 10,
                isSelected = true,
            ).apply {
                conversion = Conversion(
                    conversionValue = BigDecimal("2.24")
                )
            }

            val subject1 = currency1.hashCode()
            val subject2 = currency2.hashCode()

            subject1.shouldBe(subject2)
        }

        @Test
        fun `given two different Currency objects, when hashCode is called, then it should return different values`() {
            val currency1 = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12,
                position = 10,
                isSelected = true,
            ).apply {
                conversion = Conversion(
                    conversionValue = BigDecimal("2.24")
                )
            }
            val currency2 = Currency(
                currencyCode = "USD_ARS",
                exchangeRate = 924.7415,
                position = 6,
                isSelected = true,
            ).apply {
                conversion = Conversion(
                    conversionValue = BigDecimal("56.1485")
                )
            }

            val subject1 = currency1.hashCode()
            val subject2 = currency2.hashCode()

            subject1.shouldNotBe(subject2)
        }
    }

    @Nested
    inner class DeepCopy {

        @Test
        fun `given a Currency object, when deepCopy is called, then it should create a new instance`() {
            val subject = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12,
                position = 10,
                isSelected = true,
            )

            val copy = subject.deepCopy()

            subject.shouldNotBeSameInstanceAs(copy)
        }

        @Test
        fun `given a Currency object, when deepCopy is called, then the copied object should have the same property values`() {
            val subject = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12,
                position = 10,
                isSelected = true,
            ).apply {
                conversion = Conversion(
                    conversionValue = BigDecimal("2.2400")
                )
            }

            val copy = subject.deepCopy()

            subject.apply {
                currencyCode.shouldBe(copy.currencyCode)
                exchangeRate.shouldBe(copy.exchangeRate)
                position.shouldBe(copy.position)
                isSelected.shouldBe(copy.isSelected)
                isFocused.shouldBe(copy.isFocused)
                isInputValid.shouldBe(copy.isInputValid)
                conversion.value.shouldBe(copy.conversion.value)
                conversion.valueAsString.shouldBe(copy.conversion.valueAsString)
                conversion.hint.shouldBe(copy.conversion.hint)
            }
        }
    }

    @Nested
    inner class ToString {

        @Test
        fun `given a Currency object with default values, when toString is called, then it should return the correct String`() {
            val subject = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12
            )

            val result = subject.toString()

            result.shouldBe("{-1 EUR    }")
        }

        @Test
        fun `given a Currency object with all values, when toString is called, then it should return the correct String`() {
            val subject = Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 1.12,
                position = 12,
                isSelected = true,
            ).apply {
                isFocused = true
            }

            val result = subject.toString()
            
            result.shouldBe("{12 EUR F S}")
        }
    }
}
