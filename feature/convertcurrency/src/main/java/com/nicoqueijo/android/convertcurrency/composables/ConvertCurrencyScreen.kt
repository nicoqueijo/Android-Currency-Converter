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
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews

@Composable
fun ConvertCurrencyScreen(
    modifier: Modifier = Modifier,
    onFabClick: (() -> Unit)? = null,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxSize().weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                FloatingActionButton(
                    onClick = { onFabClick?.invoke() },
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            }
            Box {
                NumberPad(
                    state = NumberPadState(
                        digitButtonOnClick = null, // TODO: Pass the correct implementation
                        decimalPointButtonOnClick = null, // TODO: Pass the correct implementation
                        backspaceButtonOnClick = null, // TODO: Pass the correct implementation
                    )
                )
            }
        }
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyScreenPreview() {
    AndroidCurrencyConverterTheme {
        ConvertCurrencyScreen()
    }
}
