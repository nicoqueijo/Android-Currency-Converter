package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nicoqueijo.android.convertcurrency.composables.util.NumberPadState
import com.nicoqueijo.android.convertcurrency.composables.util.Digit
import com.nicoqueijo.android.convertcurrency.composables.util.NumPadKey
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews

// TODO: Style this nice
/**
 * A Composable component to display a [NumberPadState].
 * Has the function of a numerical keyboard with three buttons on each row.
 * @param state [NumberPadState] object containing the UI state and onClick events required.
 */
@Composable
fun NumberPad(
    modifier: Modifier = Modifier,
    state: NumberPadState,
) {
    Surface {
        VerticalGrid(
            modifier = modifier,
            columns = 3
        ) {
            NumPadKey.entries.forEach { entry ->
                NumberPadButton(char = entry.value) {
                    with(state) {
                        when (entry) {
                            NumPadKey.ONE -> onDigitButtonClick?.invoke(Digit.One)
                            NumPadKey.TWO -> onDigitButtonClick?.invoke(Digit.Two)
                            NumPadKey.THREE -> onDigitButtonClick?.invoke(Digit.Three)
                            NumPadKey.FOUR -> onDigitButtonClick?.invoke(Digit.Four)
                            NumPadKey.FIVE -> onDigitButtonClick?.invoke(Digit.Five)
                            NumPadKey.SIX -> onDigitButtonClick?.invoke(Digit.Six)
                            NumPadKey.SEVEN -> onDigitButtonClick?.invoke(Digit.Seven)
                            NumPadKey.EIGHT -> onDigitButtonClick?.invoke(Digit.Eight)
                            NumPadKey.NINE -> onDigitButtonClick?.invoke(Digit.Nine)
                            NumPadKey.DECIMAL_POINT -> onDecimalSeparatorButtonClick?.invoke()
                            NumPadKey.ZERO -> onDigitButtonClick?.invoke(Digit.Zero)
                            NumPadKey.BACKSPACE -> onBackspaceButtonClick?.invoke()
                        }
                    }
                }
            }
        }
    }
}

// TODO: Style this nice
/**
 * A Composable component to display a button with a single character.
 * @param modifier [Modifier] to control the internal Box element.
 * @param char The character to display on the button.
 * @param onClick The click event for the button.
 */
@Composable
fun NumberPadButton(
    modifier: Modifier = Modifier,
    char: Char,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(75.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Text(
            modifier = modifier.align(Alignment.Center),
            text = char.toString(),
            textAlign = TextAlign.Center,
            /*color = colors.foregroundNeutralMain,
            style = typography.HM._500*/
        )
    }
}

@Composable
@DarkLightPreviews
fun NumberPadPreview() {
    val state = NumberPadState()
    AndroidCurrencyConverterTheme {
        NumberPad(state = state)
    }
}
