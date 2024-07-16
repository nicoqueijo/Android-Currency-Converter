package com.nicoqueijo.android.core.extensions

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class BigDecimalExtensionsTest {

    @Test
    fun `given a decimal zero, when rounded, then should be zero with four decimal places`() {
        val value = BigDecimal("0.00000000000")
        val expected = BigDecimal("0.0000")
        val actual = value.roundToFourDecimalPlaces()
        actual.shouldBe(expected)
    }

    @Test
    fun `given an integer zero, when rounded, then should be zero with four decimal places`() {
        val value = BigDecimal("0")
        val expected = BigDecimal("0.0000")
        val actual = value.roundToFourDecimalPlaces()
        actual.shouldBe(expected)
    }

    @Test
    fun `given a decimal with four decimal places, when rounded, then should remain unchanged`() {
        val value = BigDecimal("48762.2476")
        val expected = BigDecimal("48762.2476")
        val actual = value.roundToFourDecimalPlaces()
        actual.shouldBe(expected)
    }

    @Test
    fun `given a decimal with less than four decimal places, when rounded, then should have four decimal places`() {
        val value = BigDecimal("48762.24")
        val expected = BigDecimal("48762.2400")
        val actual = value.roundToFourDecimalPlaces()
        actual.shouldBe(expected)
    }

    @Test
    fun `given a decimal with more than four decimal places, when rounded up, then should have four decimal places`() {
        val value = BigDecimal("48762.247686241")
        val expected = BigDecimal("48762.2477")
        val actual = value.roundToFourDecimalPlaces()
        actual.shouldBe(expected)
    }

    @Test
    fun `given a decimal with more than four decimal places, when rounded down, then should have four decimal places`() {
        val value = BigDecimal("48762.247635112")
        val expected = BigDecimal("48762.2476")
        val actual = value.roundToFourDecimalPlaces()
        actual.shouldBe(expected)
    }

    @Test
    fun `given a decimal ending with fives, when rounded, then should have four decimal places`() {
        val value = BigDecimal("48762.55555555555555")
        val expected = BigDecimal("48762.5556")
        val actual = value.roundToFourDecimalPlaces()
        actual.shouldBe(expected)
    }
}