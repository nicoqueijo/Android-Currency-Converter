package com.nicoqueijo.android.selectcurrency

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews

@Composable
fun SelectCurrencyScreen(
    modifier: Modifier = Modifier,
    viewModel: SelectCurrencyViewModel = hiltViewModel(),
    onCurrencyClick: (() -> Unit)? = null,
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn {
            uiState.value.filteredCurrencies.forEach { currency ->
                item {
                    SelectCurrencyRow(state = currency)
                }
            }
        }
    }
}

@DarkLightPreviews
@Composable
fun SelectCurrencyScreenPreview() {
    AndroidCurrencyConverterTheme {
        SelectCurrencyScreen()
    }
}
