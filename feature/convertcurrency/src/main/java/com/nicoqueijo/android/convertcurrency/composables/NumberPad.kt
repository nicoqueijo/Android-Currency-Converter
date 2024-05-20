package com.nicoqueijo.android.convertcurrency.composables

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nicoqueijo.android.convertcurrency.Digit
import com.nicoqueijo.android.convertcurrency.NumPadKey
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews

/**
 * A Composable component to display a [NumberPadState].
 * Has the function of a numerical keyboard with three buttons on each row.
 * @param state [NumberPadState] object containing the UI state and onClick events required.
 */
@Composable
fun NumberPad(
    state: NumberPadState,
    modifier: Modifier = Modifier
) {
    VerticalGrid(
        modifier = modifier,
        columns = 3
    ) {
        NumPadKey.entries.forEach { entry ->
            NumberPadButton(char = entry.value) {
                with(state) {
                    when (entry) {
                        NumPadKey.ONE -> digitButtonOnClick.invoke(Digit.One)
                        NumPadKey.TWO -> digitButtonOnClick.invoke(Digit.Two)
                        NumPadKey.THREE -> digitButtonOnClick.invoke(Digit.Three)
                        NumPadKey.FOUR -> digitButtonOnClick.invoke(Digit.Four)
                        NumPadKey.FIVE -> digitButtonOnClick.invoke(Digit.Five)
                        NumPadKey.SIX -> digitButtonOnClick.invoke(Digit.Six)
                        NumPadKey.SEVEN -> digitButtonOnClick.invoke(Digit.Seven)
                        NumPadKey.EIGHT -> digitButtonOnClick.invoke(Digit.Eight)
                        NumPadKey.NINE -> digitButtonOnClick.invoke(Digit.Nine)
                        NumPadKey.DECIMAL_POINT -> decimalPointButtonOnClick.invoke()
                        NumPadKey.ZERO -> digitButtonOnClick.invoke(Digit.Zero)
                        NumPadKey.BACKSPACE -> backspaceButtonOnClick.invoke()
                    }
                }
            }
        }
    }
}

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

/**
 * UI State object to be used by [NumberPad] Composable.
 * @property digitButtonOnClick The event to be executed among the click of a digit button.
 * @property decimalPointButtonOnClick The event to be executed among the click of the decimal button.
 * @property backspaceButtonOnClick The event to be executed among the click of the backspace button.
 */
data class NumberPadState(
    val digitButtonOnClick: (Digit) -> Unit,
    val decimalPointButtonOnClick: () -> Unit,
    val backspaceButtonOnClick: () -> Unit,
)

@Composable
@DarkLightPreviews
fun NumberPadPreview() {
    val state = NumberPadState(
        digitButtonOnClick = {
            Log.d("NumberPadState", "Button digit clicked.")
        },
        decimalPointButtonOnClick = {
            Log.d("NumberPadState", "Button decimal point clicked.")
        },
        backspaceButtonOnClick = {
            Log.d("NumberPadState", "Button backspace clicked.")
        }
    )
    AndroidCurrencyConverterTheme {
        NumberPad(state = state)
    }
}
