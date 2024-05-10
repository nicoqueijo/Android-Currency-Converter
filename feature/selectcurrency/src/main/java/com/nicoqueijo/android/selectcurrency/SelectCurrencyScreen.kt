package com.nicoqueijo.android.selectcurrency

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews

@Composable
fun SelectCurrencyScreen(
    modifier: Modifier = Modifier,
    onCurrencyClick: (() -> Unit)? = null
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text(
            modifier = Modifier.clickable {
                onCurrencyClick?.invoke()
            },
            text = "Select currency"
        )
    }
}

@DarkLightPreviews
@Composable
fun SelectCurrencyScreenPreview() {
    AndroidCurrencyConverterTheme {
        SelectCurrencyScreen()
    }
}
