package com.nicoqueijo.android.core.extensions

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class BigDecimalExtensionsTest {

    @Test
    fun testDecimalZero() {
        val value = BigDecimal("0.00000000000")
        val expected = BigDecimal("0.0000")
        val actual = value.roundToFourDecimalPlaces()
        actual.shouldBe(expected)
    }

    @Test
    fun testIntegerZero() {
        val value = BigDecimal("0")
        val expected = BigDecimal("0.0000")
        val actual = value.roundToFourDecimalPlaces()
        actual.shouldBe(expected)
    }

    @Test
    fun testDecimalWithFourDecimalPlaces() {
        val value = BigDecimal("48762.2476")
        val expected = BigDecimal("48762.2476")
        val actual = value.roundToFourDecimalPlaces()
        actual.shouldBe(expected)
    }

    @Test
    fun testDecimalWithLessThanFourDecimalPlaces() {
        val value = BigDecimal("48762.24")
        val expected = BigDecimal("48762.2400")
        val actual = value.roundToFourDecimalPlaces()
        actual.shouldBe(expected)
    }

    @Test
    fun testDecimalWithMoreThanFourDecimalPlacesRoundedUp() {
        val value = BigDecimal("48762.247686241")
        val expected = BigDecimal("48762.2477")
        val actual = value.roundToFourDecimalPlaces()
        actual.shouldBe(expected)
    }

    @Test
    fun testDecimalWithMoreThanFourDecimalPlacesRoundedDown() {
        val value = BigDecimal("48762.247635112")
        val expected = BigDecimal("48762.2476")
        val actual = value.roundToFourDecimalPlaces()
        actual.shouldBe(expected)
    }

    @Test
    fun testDecimalWithMoreThanFourDecimalPlacesEndingWithFives() {
        val value = BigDecimal("48762.55555555555555")
        val expected = BigDecimal("48762.5556")
        val actual = value.roundToFourDecimalPlaces()
        actual.shouldBe(expected)
    }
}