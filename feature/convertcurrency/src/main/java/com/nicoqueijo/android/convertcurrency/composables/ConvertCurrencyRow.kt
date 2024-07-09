package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.core.model.Hint
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
import com.nicoqueijo.android.ui.XS
import com.nicoqueijo.android.ui.XXL
import com.nicoqueijo.android.ui.XXS
import com.nicoqueijo.android.ui.XXXS
import com.nicoqueijo.android.ui.XXXXS
import com.nicoqueijo.android.ui.extensions.getDrawableResourceByName
import com.psoffritti.taptargetcompose.TapTargetCoordinator
import com.psoffritti.taptargetcompose.TapTargetScope
import com.psoffritti.taptargetcompose.TapTargetStyle
import com.psoffritti.taptargetcompose.TextDefinition

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TapTargetScope.ConvertCurrencyRow(
    modifier: Modifier = Modifier,
    state: Currency,
    onConversionClick: (() -> Unit)? = null,
    onRowSwipe: (() -> Unit)? = null,
    showTapTargets: Boolean = false,
) {
    var swipeHandled by remember { mutableStateOf(false) }
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = { swipeToDismissValue ->
            if (swipeToDismissValue != SwipeToDismissBoxValue.Settled) {
                swipeHandled = true
                true
            } else {
                false
            }
        },
        positionalThreshold = { totalDistance -> totalDistance * 0.5f },
    )
    LaunchedEffect(key1 = swipeHandled) {
        if (swipeHandled) {
            onRowSwipe?.invoke()
        }
    }
    SwipeToDismissBox(
        modifier = modifier,
        state = swipeToDismissBoxState,
        backgroundContent = {
            DeleteRow(dismissDirection = swipeToDismissBoxState.dismissDirection)
        },
    ) {
        val hapticFeedback = LocalHapticFeedback.current
        val clipboardManager = LocalClipboardManager.current
        Surface {
            Row(
                modifier = Modifier
                    .background(
                        color = if (state.isFocused) {
                            MaterialTheme.colorScheme.tertiary
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
                    .fillMaxWidth()
                    .height(height = XXL)
                    .padding(
                        horizontal = XS,
                        vertical = XXS,
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(vertical = XXXS)
                        .clip(shape = RoundedCornerShape(size = XXXXS))
                        .then(
                            if (showTapTargets) {
                                Modifier.tapTarget(
                                    precedence = 2,
                                    title = TextDefinition(
                                        text = "Long-press & drag any part of the row currency to reorder",
                                        textStyle = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    ),
                                    description = TextDefinition(
                                        text = "Long-press & drag any part of the row currency to reorder",
                                        textStyle = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    ),
                                    tapTargetStyle = TapTargetStyle(
                                        backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                                        tapTargetHighlightColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                        backgroundAlpha = 1f,
                                    )
                                )
                            } else {
                                Modifier
                            }
                        ),
                    contentDescription = null,
                    painter = painterResource(
                        id = LocalContext.current.getDrawableResourceByName(
                            name = state.currencyCode.lowercase()
                        )
                    )
                )
                Spacer(
                    modifier = Modifier
                        .width(width = XS)
                        .fillMaxHeight()
                )
                Text(
                    modifier = Modifier.then(
                        if (showTapTargets) {
                            Modifier.tapTarget(
                                precedence = 3,
                                title = TextDefinition(
                                    text = "Swipe any part of the row to remove",
                                    textStyle = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                ),
                                description = TextDefinition(
                                    text = "Swipe any part of the row to remove",
                                    textStyle = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                ),
                                tapTargetStyle = TapTargetStyle(
                                    backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                                    tapTargetHighlightColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                    backgroundAlpha = 1f,
                                )
                            )
                        } else {
                            Modifier
                        }
                    ),
                    text = state.trimmedCurrencyCode,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(
                    modifier = Modifier
                        .width(width = XS)
                        .fillMaxHeight()
                )
                Box(
                    modifier = Modifier.weight(weight = 1f),
                ) {
                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterEnd)
                            .combinedClickable(
                                onClick = {
                                    onConversionClick?.invoke()
                                },
                                onLongClick = {
                                    clipboardManager.setText(
                                        annotatedString = AnnotatedString(text = state.conversion.valueAsString)
                                    )
                                    hapticFeedback.performHapticFeedback(
                                        hapticFeedbackType = HapticFeedbackType.LongPress
                                    )
                                },
                            )
                            .then(
                                if (showTapTargets) {
                                    Modifier.tapTarget(
                                        precedence = 1,
                                        title = TextDefinition(
                                            text = "Long-press conversion to copy",
                                            textStyle = MaterialTheme.typography.titleLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer
                                        ),
                                        description = TextDefinition(
                                            text = "Long-press conversion to copy",
                                            textStyle = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer
                                        ),
                                        tapTargetStyle = TapTargetStyle(
                                            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                                            tapTargetHighlightColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                            backgroundAlpha = 1f,
                                        )
                                    )
                                } else {
                                    Modifier
                                }
                            ),
                        text = state.conversion.valueAsText.ifEmpty {
                            state.conversion.hint.formattedNumber
                        },
                        color = if (state.conversion.valueAsText.isEmpty()) {
                            MaterialTheme.colorScheme.onTertiary
                        } else {
                            MaterialTheme.colorScheme.primary
                        },
                        fontSize = 20.sp,
                        textAlign = TextAlign.End,
                        maxLines = 1,
                    )
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .fillMaxHeight()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = if (state.isFocused) {
                                        listOf(
                                            MaterialTheme.colorScheme.tertiary,
                                            Color.Transparent
                                        )
                                    } else {
                                        listOf(MaterialTheme.colorScheme.surface, Color.Transparent)
                                    }
                                )
                            )
                    )
                }
                if (state.isFocused) {
                    BlinkingCursor(modifier = Modifier.padding(start = XXXXS))
                } else {
                    Spacer(modifier = Modifier.width(width = XXXS))
                }
            }
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
        TapTargetCoordinator(showTapTargets = false) {
            ConvertCurrencyRow(state = currency)
        }
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
        TapTargetCoordinator(showTapTargets = false) {
            ConvertCurrencyRow(state = currency)
        }
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyRowHintPreview() {
    val currency = Currency(
        currencyCode = "USD_CAD",
        exchangeRate = 1.36175,
    ).apply {
        conversion.hint = Hint(number = "1")
    }
    AndroidCurrencyConverterTheme {
        TapTargetCoordinator(showTapTargets = false) {
            ConvertCurrencyRow(state = currency)
        }
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
        conversion.hint = Hint(number = "1")
    }
    AndroidCurrencyConverterTheme {
        TapTargetCoordinator(showTapTargets = false) {
            ConvertCurrencyRow(state = currency)
        }
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyRowLongPreview() {
    val currency = Currency(
        currencyCode = "USD_CAD",
        exchangeRate = 1.36175,
    ).apply {
        conversion.valueAsString = "9876543219876543.678"
    }
    AndroidCurrencyConverterTheme {
        TapTargetCoordinator(showTapTargets = false) {
            ConvertCurrencyRow(state = currency)
        }
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyRowLongFocusedPreview() {
    val currency = Currency(
        currencyCode = "USD_CAD",
        exchangeRate = 1.36175,
    ).apply {
        conversion.valueAsString = "9876543219876543.678"
        isFocused = true
    }
    AndroidCurrencyConverterTheme {
        TapTargetCoordinator(showTapTargets = false) {
            ConvertCurrencyRow(state = currency)
        }
    }
}