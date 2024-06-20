package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
import com.nicoqueijo.android.ui.S
import com.nicoqueijo.android.ui.XS
import com.nicoqueijo.android.ui.XXS
import com.nicoqueijo.android.ui.XXXS
import com.nicoqueijo.android.ui.extensions.getDrawableResourceByName

@Composable
fun ConvertCurrencyRow(
    modifier: Modifier = Modifier,
    state: Currency,
) {
    Surface(modifier = modifier) {
        Row(
            modifier = Modifier
                .background(
                    color = if (state.isFocused) {
                        Color.Gray
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .fillMaxWidth()
                .height(height = 56.dp)
                .padding(
                    horizontal = XS,
                    vertical = XXS,
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(vertical = XXXS)
                    .clip(shape = RoundedCornerShape(size = 2.dp)),
                contentDescription = null,
                painter = painterResource(
                    id = LocalContext.current.getDrawableResourceByName(
                        name = state.currencyCode.lowercase()
                    )
                )
            )
            Spacer(
                modifier = Modifier
                    .width(width = S)
                    .fillMaxHeight()
            )
            Text(
                text = state.trimmedCurrencyCode,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(
                modifier = Modifier
                    .width(width = S)
                    .fillMaxHeight()
            )
            // TODO: Add the fading edge to the start of this Text
            // TODO: Add a hint (conversion.hint) to this Text when it's empty
            Text(
                modifier = Modifier.weight(
                    weight = 1f,
                ),
                text = state.conversion.valueAsText.ifEmpty {
                    state.conversion.hint
                },
                color = if (state.conversion.valueAsText.isEmpty()) {
                    Color.Gray
                } else {
                    Color.Unspecified
                },
                textAlign = TextAlign.End,
                maxLines = 1,
            )
            // TODO: Add blinking cursor when focused
        }
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyRowPreview() {
    val currency = Currency(
        currencyCode = "USD_CAD",
        exchangeRate = 1.36175,
    ).apply {
        conversion.valueAsString = "12345.6789"
    }
    AndroidCurrencyConverterTheme {
        ConvertCurrencyRow(state = currency)
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyRowFocusedPreview() {
    val currency = Currency(
        currencyCode = "USD_CAD",
        exchangeRate = 1.36175,
    ).apply {
        conversion.valueAsString = "12345.6789"
        isFocused = true
    }
    AndroidCurrencyConverterTheme {
        ConvertCurrencyRow(state = currency)
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyRowHintPreview() {
    val currency = Currency(
        currencyCode = "USD_CAD",
        exchangeRate = 1.36175,
    ).apply {
        conversion.hint = "1"
    }
    AndroidCurrencyConverterTheme {
        ConvertCurrencyRow(state = currency)
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyRowFocusedHintPreview() {
    val currency = Currency(
        currencyCode = "USD_CAD",
        exchangeRate = 1.36175,
    ).apply {
        isFocused = true
        conversion.hint = "1"
    }
    AndroidCurrencyConverterTheme {
        ConvertCurrencyRow(state = currency)
    }
}
