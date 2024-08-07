package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nicoqueijo.android.convertcurrency.ConvertCurrencyViewModel
import com.nicoqueijo.android.convertcurrency.R
import com.nicoqueijo.android.convertcurrency.composables.util.NumberPadState
import com.nicoqueijo.android.convertcurrency.model.UiEvent
import com.nicoqueijo.android.convertcurrency.model.UiState
import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.core.model.Position
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
import com.nicoqueijo.android.ui.S
import com.nicoqueijo.android.ui.XL
import com.nicoqueijo.android.ui.XXXS
import com.psoffritti.taptargetcompose.TapTargetCoordinator
import kotlinx.coroutines.launch
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState
import java.math.BigDecimal
import java.util.Locale

/**
 * Composable function for the Convert Currency Screen.
 *
 * This function serves as the main entry point for the currency conversion feature,
 * integrating the ViewModel to manage state and handle events. It extracts the state from the ViewModel
 * and passes it to the [ConvertCurrency] composable, making it possible to preview the UI without
 * requiring the ViewModel to be instantiated manually.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param viewModel The ViewModel instance obtained via Hilt.
 * @param onFabClick Lambda function to be invoked when the Floating Action Button (FAB) is clicked.
 */
@Composable
fun ConvertCurrencyScreen(
    modifier: Modifier = Modifier,
    viewModel: ConvertCurrencyViewModel? = hiltViewModel(),
    onFabClick: (() -> Unit)? = null,
) {
    val uiState = viewModel?.uiState?.collectAsStateWithLifecycle()?.value
    ConvertCurrency(
        modifier = modifier,
        state = uiState,
        onFabClick = onFabClick,
        onEvent = { event ->
            viewModel?.onEvent(event = event)
        },
    )
}

/**
 * Composable function for the Convert Currency UI.
 *
 * This function provides a UI for converting currencies, including a top app bar,
 * a list of currencies that can be reordered and removed by swiping, and a number pad for input.
 * It also includes a Floating Action Button (FAB) for adding new currencies. It is designed
 * to be previewed independently of the ViewModel, enabling easier UI testing and development.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param state The UI state containing the list of currencies and other UI-related flags.
 * @param onFabClick Lambda function to be invoked when the Floating Action Button (FAB) is clicked.
 * @param onEvent Lambda function to handle UI events such as currency selection, reordering, removal
 * by swiping, and keyboard input processing.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ConvertCurrency(
    modifier: Modifier = Modifier,
    state: UiState?,
    onFabClick: (() -> Unit)? = null,
    onEvent: ((UiEvent) -> Unit)? = null,
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Scaffold(
            topBar = {
                Column {
                    TopAppBar(
                        navigationIcon = {
                            Row {
                                Spacer(
                                    modifier = Modifier.width(XXXS)
                                )
                                Image(
                                    modifier = Modifier.size(size = XL),
                                    painter = painterResource(id = R.drawable.app_icon),
                                    contentDescription = null
                                )
                            }
                        },
                        title = {
                            Text(
                                modifier = Modifier.padding(start = XXXS),
                                text = stringResource(id = R.string.app_name),
                                color = MaterialTheme.colorScheme.primary,
                            )
                        },
                        actions = {
                            if (state?.currencies?.isNotEmpty() == true) {
                                IconButton(
                                    onClick = {
                                        onEvent?.invoke(UiEvent.UnselectAllCurrencies)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                    )
                                }
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    HorizontalDivider()
                }
            }
        ) { innerPadding ->
            var rememberedCurrencies by remember { mutableStateOf(state?.currencies?.toMutableStateList()) } // Wrapping in a remember is required to enable reordering.
            rememberedCurrencies =
                state?.currencies?.toMutableStateList() // Assignment allows currencies to show up on the screen.
            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(paddingValues = innerPadding),
            ) {
                if (state?.showDialog == true) {
                    RemoveCurrenciesDialog(
                        onConfirmClick = {
                            onEvent?.invoke(UiEvent.ConfirmDialog)
                        },
                        onDismissClick = {
                            onEvent?.invoke(UiEvent.CancelDialog)
                        },
                    )
                }
                Column {
                    TapTargetCoordinator(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(weight = 1f),
                        showTapTargets = state?.isFirstLaunch == true,
                        onComplete = {
                            onEvent?.invoke(
                                UiEvent.ToggleOffIsFirstLaunch
                            )
                        },
                    ) {
                        Box(
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            if (state?.currencies?.isEmpty() == true) {
                                EmptyListIndicator()
                            } else {
                                val lazyListState = rememberLazyListState()
                                val reorderableLazyColumnState =
                                    rememberReorderableLazyListState(lazyListState) { from, to ->
                                        rememberedCurrencies = rememberedCurrencies?.apply {
                                            add(to.index, removeAt(from.index))
                                        }
                                    }
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(vertical = (0.15).dp), // https://github.com/Calvin-LL/Reorderable/issues/32#issuecomment-2099453540
                                    state = lazyListState,
                                ) {
                                    items(
                                        items = rememberedCurrencies?.toList() ?: emptyList(),
                                        key = { currency -> currency.hashCode() }
                                    ) { currency ->
                                        ReorderableItem(
                                            state = reorderableLazyColumnState,
                                            key = currency.hashCode(),
                                        ) {
                                            ConvertCurrencyRow(
                                                modifier = Modifier
                                                    .animateItem()
                                                    .longPressDraggableHandle {
                                                        onEvent?.invoke(
                                                            UiEvent.ReorderCurrencies(currencies = rememberedCurrencies!!.toList())
                                                        )
                                                    },
                                                state = currency,
                                                onConversionClick = {
                                                    onEvent?.invoke(
                                                        UiEvent.SetCurrencyFocus(currency = currency)
                                                    )
                                                },
                                                onRowSwipe = {
                                                    onEvent?.invoke(
                                                        UiEvent.UnselectCurrency(currency = currency)
                                                    )
                                                    coroutineScope.launch {
                                                        snackbarHostState.currentSnackbarData?.dismiss()
                                                        val result = snackbarHostState.showSnackbar(
                                                            message = context.getString(R.string.item_removed_label),
                                                            actionLabel = context.getString(R.string.undo_label),
                                                            duration = SnackbarDuration.Short,
                                                        )
                                                        when (result) {
                                                            SnackbarResult.ActionPerformed -> {
                                                                onEvent?.invoke(
                                                                    UiEvent.RestoreCurrency(currency = currency)
                                                                )
                                                            }

                                                            else -> {
                                                                // Do nothing
                                                            }
                                                        }
                                                    }
                                                },
                                                showTapTargets = currency.position == Position.FIRST.value
                                            )
                                        }
                                        HorizontalDivider()
                                    }
                                    item {
                                        // Ensures the Floating Action Button (FAB) does not obscure the last item when the list is scrolled to its bottommost position.
                                        Spacer(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(88.dp)
                                        )
                                    }
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .height(72.dp)
                                    .fillMaxWidth()
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                MaterialTheme.colorScheme.surface,
                                            )
                                        )
                                    )
                            )
                            SnackbarHost(
                                modifier = Modifier.padding(bottom = 72.dp),
                                hostState = snackbarHostState,
                                snackbar = { data ->
                                    Snackbar(
                                        snackbarData = data,
                                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                        contentColor = MaterialTheme.colorScheme.secondary,
                                        actionColor = MaterialTheme.colorScheme.tertiary,
                                    )
                                }
                            )
                            FloatingActionButton(
                                modifier = Modifier
                                    .padding(bottom = S)
                                    .tapTarget(
                                        tapTargetDefinition = addCurrencyTapTargetDefinition()
                                    ),
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.secondary,
                                onClick = { onFabClick?.invoke() },
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                    Box {
                        NumberPad(
                            state = NumberPadState(
                                locale = Locale.getDefault(),
                                onKeyboardButtonClick = { keyboardInput ->
                                    onEvent?.invoke(
                                        UiEvent.ProcessKeyboardInput(keyboardInput = keyboardInput)
                                    )
                                },
                                onKeyboardButtonLongClick = { keyboardInput ->
                                    UiEvent.ProcessKeyboardInput(keyboardInput = keyboardInput)
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyScreenPreview() {
    val state = UiState(
        currencies = listOf(
            Currency(
                currencyCode = "USD_GBP",
                exchangeRate = 0.786829,
                position = 0,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("22.4246265")
            },
            Currency(
                currencyCode = "USD_USD",
                exchangeRate = 1.0,
                position = 1,
                isSelected = true,
            ).apply {
                isFocused = true
                conversion.value = BigDecimal("28.5")
            },
            Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 0.931032,
                position = 2,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("26.534412")
            },
            Currency(
                currencyCode = "USD_JPY",
                exchangeRate = 157.837,
                position = 3,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("4498.3545")
            },
        )
    )
    AndroidCurrencyConverterTheme {
        ConvertCurrency(state = state)
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyEmptyScreenPreview() {
    val state = UiState()
    AndroidCurrencyConverterTheme {
        ConvertCurrency(state = state)
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyScreenWithDialogPreview() {
    val state = UiState(
        currencies = listOf(
            Currency(
                currencyCode = "USD_GBP",
                exchangeRate = 0.786829,
                position = 0,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("22.4246265")
            },
            Currency(
                currencyCode = "USD_USD",
                exchangeRate = 1.0,
                position = 1,
                isSelected = true,
            ).apply {
                isFocused = true
                conversion.value = BigDecimal("28.5")
            },
            Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 0.931032,
                position = 2,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("26.534412")
            },
            Currency(
                currencyCode = "USD_JPY",
                exchangeRate = 157.837,
                position = 3,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("4498.3545")
            },
        ),
        showDialog = true,
    )
    AndroidCurrencyConverterTheme {
        ConvertCurrency(state = state)
    }
}

@DarkLightPreviews
@Composable
fun ConvertCurrencyScreenManyCurrenciesPreview() {
    val state = UiState(
        currencies = listOf(
            Currency(
                currencyCode = "USD_GBP",
                exchangeRate = 0.786829,
                position = 0,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("22.4246265")
            },
            Currency(
                currencyCode = "USD_USD",
                exchangeRate = 1.0,
                position = 1,
                isSelected = true,
            ).apply {
                isFocused = true
                conversion.value = BigDecimal("28.5")
            },
            Currency(
                currencyCode = "USD_EUR",
                exchangeRate = 0.931032,
                position = 2,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("26.534412")
            },
            Currency(
                currencyCode = "USD_JPY",
                exchangeRate = 157.837,
                position = 3,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("4498.3545")
            },
            Currency(
                currencyCode = "USD_CHF",
                exchangeRate = 0.898383,
                position = 4,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("25.1547")
            },
            Currency(
                currencyCode = "USD_CNY",
                exchangeRate = 7.2673,
                position = 5,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("203.4844")
            },
            Currency(
                currencyCode = "USD_KRW",
                exchangeRate = 1381.270188,
                position = 6,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("38675.5653")
            },
            Currency(
                currencyCode = "USD_CAD",
                exchangeRate = 1.36734,
                position = 7,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("38.2855")
            },
            Currency(
                currencyCode = "USD_RUB",
                exchangeRate = 85.500917,
                position = 8,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("2394.0257")
            },
            Currency(
                currencyCode = "USD_HKD",
                exchangeRate = 7.808621,
                position = 9,
                isSelected = true,
            ).apply {
                conversion.value = BigDecimal("218.6414")
            },
        )
    )
    AndroidCurrencyConverterTheme {
        ConvertCurrency(state = state)
    }
}
