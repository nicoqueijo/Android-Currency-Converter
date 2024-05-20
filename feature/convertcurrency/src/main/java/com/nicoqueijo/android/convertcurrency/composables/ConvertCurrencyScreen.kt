package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nicoqueijo.android.convertcurrency.Digit
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews

// TODO: Style this nicely
@Composable
fun ConvertCurrencyScreen(
    modifier: Modifier = Modifier,
    state: ConvertCurrencyScreenState,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                EmptyListIndicator() // TODO: Only display if no currencies selected
                FloatingActionButton(
                    onClick = { state.onFabClick?.invoke() },
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            }
            Box {
                NumberPad(
                    state = NumberPadState(
                        onDigitButtonClick = null, // TODO: Pass the correct implementation
                        onDecimalPointButtonClick = null, // TODO: Pass the correct implementation
                        onBackspaceButtonClick = null, // TODO: Pass the correct implementation
                    )
                )
            }
        }
    }
}

// TODO: Figure out if I should keep this here or put in another file
data class ConvertCurrencyScreenState(
    val onFabClick: (() -> Unit)? = null,
    val onDigitButtonClick: ((Digit) -> Unit)? = null,
    val onDecimalPointButtonClick: (() -> Unit)? = null,
    val onBackspaceButtonClick: (() -> Unit)? = null,
)

@DarkLightPreviews
@Composable
fun ConvertCurrencyScreenPreview() {
    AndroidCurrencyConverterTheme {
        val state = ConvertCurrencyScreenState()
        ConvertCurrencyScreen(
            state = state
        )
    }
}
