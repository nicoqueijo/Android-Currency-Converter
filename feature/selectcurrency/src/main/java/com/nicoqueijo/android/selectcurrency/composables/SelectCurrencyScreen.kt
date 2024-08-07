package com.nicoqueijo.android.selectcurrency.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.selectcurrency.R
import com.nicoqueijo.android.selectcurrency.SelectCurrencyViewModel
import com.nicoqueijo.android.selectcurrency.model.UiEvent
import com.nicoqueijo.android.selectcurrency.model.UiState
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
import com.nicoqueijo.android.ui.L
import com.nicoqueijo.android.ui.S
import com.nicoqueijo.android.ui.XL

/**
 * Composable function for the Select Currency Screen.
 *
 * This function is the main entry point for the Select Currency feature, integrating
 * the ViewModel to manage state and handle events. It extracts the state from the ViewModel
 * and passes it to the [SelectCurrency] composable, making it possible to preview the UI without
 * requiring the ViewModel to be instantiated manually.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param viewModel The ViewModel instance obtained via Hilt.
 * @param onCurrencyClick Lambda function to be invoked when a currency is clicked.
 */
@Composable
fun SelectCurrencyScreen(
    modifier: Modifier = Modifier,
    viewModel: SelectCurrencyViewModel? = hiltViewModel(),
    onCurrencyClick: (() -> Unit)? = null,
) {
    val uiState = viewModel?.uiState?.collectAsStateWithLifecycle()?.value
    SelectCurrency(
        modifier = modifier,
        state = uiState,
        onCurrencyClick = {
            onCurrencyClick?.invoke()
        },
        onEvent = { event ->
            viewModel?.onEvent(event = event)
        },
    )
}

/**
 * Composable function for the Select Currency UI.
 *
 * This function provides a UI for selecting currencies, including a search bar,
 * a list of filtered currencies, and a placeholder for no results found. It is designed
 * to be previewed independently of the ViewModel, enabling easier UI testing and development.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param state The UI state containing the filtered currencies and search term.
 * @param onCurrencyClick Lambda function to be invoked when a currency is clicked.
 * @param onEvent Lambda function to handle UI events such as search term change and currency selection.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCurrency(
    modifier: Modifier = Modifier,
    state: UiState?,
    onCurrencyClick: (() -> Unit)? = null,
    onEvent: ((UiEvent) -> Unit)? = null,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Scaffold(
            topBar = {
                Column {
                    TopAppBar(
                        title = {
                            DockedSearchBar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = S),
                                query = state?.searchTerm ?: "",
                                onQueryChange = { queryChange ->
                                    onEvent?.invoke(
                                        UiEvent.SearchTermChange(searchTerm = queryChange)
                                    )
                                },
                                onSearch = {
                                    keyboardController?.hide()
                                },
                                active = false,
                                onActiveChange = { },
                                placeholder = {
                                    Text(text = stringResource(id = R.string.search_hint))
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        modifier = Modifier.clickable {
                                            onEvent?.invoke(
                                                UiEvent.SearchTermChange(searchTerm = "")
                                            )
                                        },
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                    )
                                },
                                colors = SearchBarDefaults.colors(
                                    inputFieldColors = SearchBarDefaults.inputFieldColors().copy(
                                        focusedTextColor = MaterialTheme.colorScheme.primary,
                                    ),
                                    containerColor = MaterialTheme.colorScheme.onSurface,
                                )
                            ) { }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.onSurface,
                        ),
                    )
                    HorizontalDivider()
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.surface)
                    .fillMaxSize()
                    .padding(paddingValues = innerPadding),
            ) {
                if (state?.isSearchResultEmpty == true) {
                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                horizontal = L,
                                vertical = XL,
                            ),
                        text = stringResource(
                            id = R.string.no_results_label,
                            state.searchTerm
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = state?.filteredCurrencies ?: emptyList(),
                            key = { currency -> currency.hashCode() }
                        ) { currency ->
                            SelectCurrencyRow(
                                modifier = Modifier.animateItem(),
                                state = currency,
                                onClick = {
                                    onEvent?.invoke(
                                        UiEvent.SelectCurrency(currency = currency)
                                    )
                                    onCurrencyClick?.invoke()
                                },
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@DarkLightPreviews
@Composable
fun SelectCurrencyScreenPreview() {
    val state = UiState(
        filteredCurrencies = listOf(
            Currency(
                currencyCode = "USD_ARS",
                exchangeRate = 905.75429,
                isSelected = true,
            ),
            Currency(
                currencyCode = "USD_CLP",
                exchangeRate = 935.45,
                isSelected = true,
            ),
            Currency(
                currencyCode = "USD_COP",
                exchangeRate = 4121.178068,
            ),
            Currency(
                currencyCode = "USD_CUP",
                exchangeRate = 25.75,
            ),
            Currency(
                currencyCode = "USD_DOP",
                exchangeRate = 59.183449,
            ),
            Currency(
                currencyCode = "USD_MXN",
                exchangeRate = 18.4103,
                isSelected = true,
            ),
            Currency(
                currencyCode = "USD_UYU",
                exchangeRate = 39.312329,
            ),
        ),
        searchTerm = "peso",
    )
    AndroidCurrencyConverterTheme {
        SelectCurrency(state = state)
    }
}

@DarkLightPreviews
@Composable
fun SelectCurrencyEmptyScreenPreview() {
    val state = UiState(
        searchTerm = "german deutsche",
        isSearchResultEmpty = true,
    )
    AndroidCurrencyConverterTheme {
        SelectCurrency(state = state)
    }
}
