package com.nicoqueijo.android.convertcurrency

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews

@Composable
fun ConvertCurrencyScreen(modifier: Modifier = Modifier) {
    Text(text = "ConvertCurrencyScreen")
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyScreenPreview() {
    AndroidCurrencyConverterTheme {
        ConvertCurrencyScreen()
    }
}
