package com.nicoqueijo.android.core.extensions

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class LongExtensionsTest {

    @Nested
    inner class LongToSeconds {

        @Test
        fun `given zero millis, when converted to seconds, then should return zero seconds`() {
            val timeInMillis = 0L
            val actual = timeInMillis.toSeconds()
            val expected = 0L
            actual.shouldBe(expected)
        }

        @Test
        fun `given under 1000 millis, when converted to seconds, then should return zero seconds`() {
            val timeInMillis = 345L
            val actual = timeInMillis.toSeconds()
            val expected = 0L
            actual.shouldBe(expected)
        }

        @Test
        fun `given millions of millis, when converted to seconds, then should return correct seconds`() {
            val timeInMillis = 3_600_000L
            val actual = timeInMillis.toSeconds()
            val expected = 3_600L
            actual.shouldBe(expected)
        }

        @Test
        fun `given trillions of millis, when converted to seconds, then should return correct seconds`() {
            val timeInMillis = 1_604_779_208_123L
            val actual = timeInMillis.toSeconds()
            val expected = 1_604_779_208L
            actual.shouldBe(expected)
        }

        @Test
        fun `given trillions of millis, when truncated and converted to seconds, then should return correct seconds`() {
            val timeInMillis = 1_604_779_208_987L
            val actual = timeInMillis.toSeconds()
            val expected = 1_604_779_208L
            actual.shouldBe(expected)
        }
    }

    @Nested
    inner class LongToMillis {

        @Test
        fun `given zero seconds, when converted to millis, then should return zero millis`() {
            val timeInSeconds = 0L
            val actual = timeInSeconds.toMillis()
            val expected = 0L
            actual.shouldBe(expected)
        }

        @Test
        fun `given thousands of seconds, when converted to millis, then should return correct millis`() {
            val timeInSeconds = 3_600L
            val actual = timeInSeconds.toMillis()
            val expected = 3_600_000L
            actual.shouldBe(expected)
        }

        @Test
        fun `given millions of seconds, when converted to millis, then should return correct millis`() {
            val timeInSeconds = 1_604_779_208L
            val actual = timeInSeconds.toMillis()
            val expected = 1_604_779_208_000L
            actual.shouldBe(expected)
        }
    }
}
