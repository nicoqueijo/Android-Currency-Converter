package com.nicoqueijo.android.currencyconverter.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nicoqueijo.android.currencyconverter.R
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
import com.nicoqueijo.android.ui.L
import com.nicoqueijo.android.ui.XL
import com.nicoqueijo.android.ui.XS

/**
 * Represents a composable UI for displaying an error state screen.
 *
 * @param modifier An optional [Modifier] to customize the Composable's layout and appearance.
 */
@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = L, vertical = XL),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = XS)
        ) {
            Icon(
                modifier = Modifier.size(size = 125.dp),
                imageVector = Icons.Default.Info,
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.error_title),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.error_description),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
    }

}

@DarkLightPreviews
@Composable
fun ErrorScreenPreview() {
    AndroidCurrencyConverterTheme {
        ErrorScreen()
    }
}
