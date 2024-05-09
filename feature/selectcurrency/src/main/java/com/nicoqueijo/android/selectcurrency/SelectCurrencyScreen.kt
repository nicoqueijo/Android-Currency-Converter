package com.nicoqueijo.android.selectcurrency

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews

@Composable
fun SelectCurrencyScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
    }
}

@DarkLightPreviews
@Composable
fun SelectCurrencyScreenPreview() {
    AndroidCurrencyConverterTheme {
        SelectCurrencyScreen()
    }
}
