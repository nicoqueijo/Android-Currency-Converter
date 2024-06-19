@file:OptIn(ExperimentalMaterial3Api::class)

package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nicoqueijo.android.convertcurrency.ConvertCurrencyUiEvent
import com.nicoqueijo.android.convertcurrency.ConvertCurrencyUiState
import com.nicoqueijo.android.convertcurrency.ConvertCurrencyViewModel
import com.nicoqueijo.android.convertcurrency.Digit
import com.nicoqueijo.android.convertcurrency.R
import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
import com.nicoqueijo.android.ui.XL

// TODO: Style this nicely
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

@Composable
fun ConvertCurrency(
    modifier: Modifier = Modifier,
    state: ConvertCurrencyUiState?,
    onFabClick: (() -> Unit)? = null,
    onEvent: ((ConvertCurrencyUiEvent) -> Unit)? = null,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                Column {
                    TopAppBar(
                        navigationIcon = {
                            Image(
                                modifier = Modifier.size(size = XL),
                                painter = painterResource(id = R.drawable.app_icon),
                                contentDescription = null
                            )
                        },
                        title = {
                            Text(text = stringResource(id = R.string.app_name))
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    onEvent?.invoke(ConvertCurrencyUiEvent.RemoveAllCurrencies)
                                }
                            ) {
                                if (state?.selectedCurrencies?.isNotEmpty() == true) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                    )
                                }
                            }
                        }
                    )
                    HorizontalDivider()
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(paddingValues = innerPadding),
            ) {
                if (state?.showDialog == true) {
                    RemoveCurrenciesDialog(
                        onConfirmClick = {
                            onEvent?.invoke(ConvertCurrencyUiEvent.ConfirmDialog)
                        },
                        onDismissClick = {
                            onEvent?.invoke(ConvertCurrencyUiEvent.CancelDialog)
                        },
                    )
                }
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        when (state?.selectedCurrencies?.isEmpty()) {
                            true -> {
                                EmptyListIndicator()
                            }

                            else -> {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    state?.selectedCurrencies?.forEach { currency ->
                                        item {
                                            ConvertCurrencyRow(
                                                modifier = Modifier.animateItem(),
                                                state = currency
                                            )
                                            HorizontalDivider()
                                        }
                                    }
                                    item {
                                        // Ensures the Floating Action Button (FAB) does not obscure the last item when the list is scrolled to its bottommost position.
                                        Spacer(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(76.dp)
                                        )
                                    }
                                }
                            }
                        }
                        FloatingActionButton(
                            onClick = { onFabClick?.invoke() },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null,
                            )
                        }
                    }
                    Box {
                        NumberPad(
                            state = NumberPadState(
                                onDigitButtonClick = null, // TODO: Pass the correct implementation
                                onDecimalSeparatorButtonClick = null, // TODO: Pass the correct implementation
                                onBackspaceButtonClick = null, // TODO: Pass the correct implementation
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
    val state = ConvertCurrencyUiState(
        selectedCurrencies = listOf(
            Currency(
                currencyCode = "USD_USD",
                exchangeRate = 1.0,
                isSelected = true,
            ),
        )
    )
    AndroidCurrencyConverterTheme {
        ConvertCurrency(state = state)
    }
}
