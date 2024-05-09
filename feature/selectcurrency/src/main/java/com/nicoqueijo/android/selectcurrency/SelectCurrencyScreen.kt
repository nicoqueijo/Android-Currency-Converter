package com.nicoqueijo.android.selectcurrency

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews

@Composable
fun SelectCurrencyScreen(modifier: Modifier = Modifier) {
    Text(text = "SelectCurrencyScreen")
}

@DarkLightPreviews
@Composable
fun SelectCurrencyScreenPreview() {
    AndroidCurrencyConverterTheme {
        SelectCurrencyScreen()
    }
}
