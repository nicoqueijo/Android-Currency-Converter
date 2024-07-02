package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
import com.nicoqueijo.android.ui.L
import com.nicoqueijo.android.ui.Red
import com.nicoqueijo.android.ui.XXL
import com.nicoqueijo.android.ui.XXS

@Composable
fun DeleteRow(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = Red,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(height = XXL)
                .padding(horizontal = XXS),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            DeleteIcon()
            DeleteIcon()
        }
    }
}

@Composable
fun DeleteIcon() {
    Icon(
        modifier = Modifier.size(L),
        imageVector = Icons.Filled.Delete,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
    )
}

@DarkLightPreviews
@Composable
fun DeleteRowPreview() {
    AndroidCurrencyConverterTheme {
        DeleteRow()
    }
}