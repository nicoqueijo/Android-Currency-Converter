package com.nicoqueijo.android.convertcurrency

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
import com.nicoqueijo.android.ui.extensions.getDrawableResourceByName

@Composable
fun ConvertCurrencyRow(
    modifier: Modifier = Modifier,
    state: Currency,
) {
    Row(
        modifier = modifier
            .background(color = Color.White) // TODO: should be black/white according to the theme
            .fillMaxWidth()
            .height(56.dp)
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp,
            )
    ) {
        Image(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .clip(shape = RoundedCornerShape(2.dp)),
            contentDescription = null,
            painter = painterResource(
                id = LocalContext.current.getDrawableResourceByName(name = state.currencyCode.lowercase())
            )
        )

        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = state.trimmedCurrencyCode,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyRowPreview() {
    AndroidCurrencyConverterTheme {
        val currency = Currency(
            currencyCode = "USD_CAD",
            exchangeRate = 1.36175,
        )
        Column {
            ConvertCurrencyRow(state = currency)
        }
    }
}
