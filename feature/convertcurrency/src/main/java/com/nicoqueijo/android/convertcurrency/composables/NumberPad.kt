package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicoqueijo.android.convertcurrency.composables.util.Digit
import com.nicoqueijo.android.convertcurrency.composables.util.NumPadKey
import com.nicoqueijo.android.convertcurrency.composables.util.NumberPadState
import com.nicoqueijo.android.convertcurrency.util.KeyboardInput
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun NumberPad(
    modifier: Modifier = Modifier,
    state: NumberPadState,
) {
    Surface(modifier = modifier) {
        VerticalGrid(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.onSurface
            ),
            columns = 3
        ) {
            NumPadKey.entries.forEach { entry ->
                if (entry == NumPadKey.DECIMAL_SEPARATOR) {
                    entry.value = DecimalFormatSymbols.getInstance(state.locale).decimalSeparator
                }
                NumberPadButton(char = entry.value) {
                    with(state) {
                        when (entry) {
                            NumPadKey.ONE -> onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.One)
                            )

                            NumPadKey.TWO -> onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Two)
                            )

                            NumPadKey.THREE -> onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Three)
                            )

                            NumPadKey.FOUR -> onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Four)
                            )

                            NumPadKey.FIVE -> onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Five)
                            )

                            NumPadKey.SIX -> onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Six)
                            )

                            NumPadKey.SEVEN -> onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Seven)
                            )

                            NumPadKey.EIGHT -> onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Eight)
                            )

                            NumPadKey.NINE -> onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Nine)
                            )

                            NumPadKey.DECIMAL_SEPARATOR -> onKeyboardButtonClick?.invoke(
                                KeyboardInput.DecimalSeparator
                            )

                            NumPadKey.ZERO -> onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Zero)
                            )

                            NumPadKey.BACKSPACE -> onKeyboardButtonClick?.invoke(
                                KeyboardInput.Backspace
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NumberPadButton(
    modifier: Modifier = Modifier,
    char: Char,
    onClick: () -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current
    TextButton(
        modifier = modifier.size(75.dp),
        onClick = {
            hapticFeedback.performHapticFeedback(
                hapticFeedbackType = HapticFeedbackType.LongPress
            )
            onClick.invoke()
        },
        shape = RectangleShape,
    ) {
        Text(
            text = char.toString(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 28.sp,
        )
    }
}

@Composable
@DarkLightPreviews
fun NumberPadPreview() {
    val state = NumberPadState(
        locale = Locale.US
    )
    AndroidCurrencyConverterTheme {
        NumberPad(state = state)
    }
}

@Composable
@DarkLightPreviews
fun NumberPadGermanLocalePreview() {
    val state = NumberPadState(
        locale = Locale.GERMAN
    )
    AndroidCurrencyConverterTheme {
        NumberPad(state = state)
    }
}