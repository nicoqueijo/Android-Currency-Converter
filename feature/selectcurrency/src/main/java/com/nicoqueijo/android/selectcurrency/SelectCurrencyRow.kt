package com.nicoqueijo.android.selectcurrency

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews

@Composable
fun CurrencyRow(
    modifier: Modifier = Modifier,
    state: Currency,
) {
    Row(modifier = modifier.height(64.dp)) {
        Card(
            modifier = Modifier
                .size(50.dp, 37.dp)
                .padding(start = 4.dp, end = 8.dp),
            shape = RoundedCornerShape(2.dp)
        ) {
            // Assuming you have an image asset for the flag
            Image(
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
                painter = painterResource(
                    id = getDrawableResourceByName(
                        name = state.currencyCode.lowercase(),
                        context = LocalContext.current
                    )
                )
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = state.trimmedCurrencyCode,
                fontSize = 18.sp,
            )
            Text(
                text = state.currencyCode,
                fontSize = 16.sp,
                maxLines = 1,
            )
        }
        if (state.isSelected) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = "Selected currency"
            )
        }
    }
}

@DarkLightPreviews
@Composable
fun CurrencyRowPreview() {
    AndroidCurrencyConverterTheme {
        val selectedCurrency = Currency(
            currencyCode = "USD_USD",
            exchangeRate = 1.0,
            isSelected = true,
        )
        val unselectedCurrency = Currency(
            currencyCode = "USD_CAD",
            exchangeRate = 1.36175,
            isSelected = false,
        )
        Column {
            CurrencyRow(state = selectedCurrency)
            CurrencyRow(state = unselectedCurrency)
        }
    }
}

/**
 * Retrieves string resources using a String instead of an int.
 * Credit: https://stackoverflow.com/a/11595723/5906793
 */
fun getStringResourceByName(name: String, context: Context): String {
    val resId = context.resources.getIdentifier(name, "string", context.packageName)
    return context.getString(resId)
}

/**
 * Retrieves drawable resources using a String instead of an int.
 * Credit: https://stackoverflow.com/a/11595723/5906793
 */
fun getDrawableResourceByName(name: String, context: Context): Int {
    return context.resources.getIdentifier(name, "drawable", context.packageName)
}