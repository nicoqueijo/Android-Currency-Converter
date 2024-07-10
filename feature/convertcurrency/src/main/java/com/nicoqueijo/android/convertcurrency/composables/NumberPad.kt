package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

/**
 * Composable function for a Number Pad.
 *
 * This function provides a number pad UI with keys for digits, decimal separator, and backspace.
 * It uses a [VerticalGrid] to arrange the keys in a 3-column layout and handles click and long-click
 * events to process input. The decimal separator is localized based on the provided state.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param state The state containing the locale and event handlers for keyboard input.
 */
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
            NumPadKey.entries.forEach { key ->
                if (key == NumPadKey.DECIMAL_SEPARATOR) {
                    key.value = DecimalFormatSymbols.getInstance(state.locale).decimalSeparator
                }
                NumberPadButton(
                    char = key.value,
                    onClick = {
                        when (key) {
                            NumPadKey.ONE -> state.onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.One)
                            )

                            NumPadKey.TWO -> state.onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Two)
                            )

                            NumPadKey.THREE -> state.onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Three)
                            )

                            NumPadKey.FOUR -> state.onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Four)
                            )

                            NumPadKey.FIVE -> state.onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Five)
                            )

                            NumPadKey.SIX -> state.onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Six)
                            )

                            NumPadKey.SEVEN -> state.onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Seven)
                            )

                            NumPadKey.EIGHT -> state.onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Eight)
                            )

                            NumPadKey.NINE -> state.onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Nine)
                            )

                            NumPadKey.DECIMAL_SEPARATOR -> state.onKeyboardButtonClick?.invoke(
                                KeyboardInput.DecimalSeparator
                            )

                            NumPadKey.ZERO -> state.onKeyboardButtonClick?.invoke(
                                KeyboardInput.Number(digit = Digit.Zero)
                            )

                            NumPadKey.BACKSPACE -> state.onKeyboardButtonClick?.invoke(
                                KeyboardInput.Backspace()
                            )
                        }
                    },
                    onLongClick = if (key == NumPadKey.BACKSPACE) {
                        {
                            state.onKeyboardButtonClick?.invoke(
                                KeyboardInput.Backspace(isLongClick = true)
                            )
                        }
                    } else null
                )
            }
        }
    }
}

/**
 * Composable function for a Number Pad Button.
 *
 * This function provides a button for the number pad, displaying a character and handling click
 * and long-click events with haptic feedback. The button can trigger different actions based on
 * the provided event handlers.
 *
 * @param modifier The modifier to be applied to the button.
 * @param char The character to be displayed on the button.
 * @param onClick Lambda function to be invoked when the button is clicked.
 * @param onLongClick Lambda function to be invoked when the button is long-clicked.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NumberPadButton(
    modifier: Modifier = Modifier,
    char: Char,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
) {
    val hapticFeedback = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .size(75.dp)
            .combinedClickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    onClick?.apply {
                        hapticFeedback.performHapticFeedback(
                            hapticFeedbackType = HapticFeedbackType.LongPress
                        )
                        invoke()
                    }
                },
                onLongClick = {
                    onLongClick?.apply {
                        hapticFeedback.performHapticFeedback(
                            hapticFeedbackType = HapticFeedbackType.LongPress
                        )
                        invoke()
                    }
                },
            )
    ) {
        Text(
            modifier = modifier.align(Alignment.Center),
            text = char.toString(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 32.sp,
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
