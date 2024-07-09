package com.nicoqueijo.android.currencyconverter.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicoqueijo.android.currencyconverter.R
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
import com.nicoqueijo.android.ui.L
import com.nicoqueijo.android.ui.S
import com.nicoqueijo.android.ui.XL
import com.nicoqueijo.android.ui.XXS
import com.nicoqueijo.android.ui.XXXS
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

/**
 * A composable function that displays a splash screen with a top bar, a circular progress indicator,
 * and a title. The splash screen attempts to fetch data on launch and invokes the appropriate callback
 * based on the success or failure of the data retrieval.
 *
 * @param modifier A [Modifier] to be applied to the [Scaffold] layout.
 * @param viewModel An optional [SplashViewModel] for fetching data.
 * @param onSuccess A lambda function to be invoked when data retrieval is successful.
 * @param onFailure A lambda function to be invoked when data retrieval fails.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel? = hiltViewModel(),
    onSuccess: (() -> Unit)? = null,
    onFailure: (() -> Unit)? = null,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                actions = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onSurface
                ),
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = L,
                    vertical = XL
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(space = S)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(size = 125.dp)
                        .padding(all = XXXS),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = XXS
                )
                Text(
                    text = stringResource(id = R.string.splash_title),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel?.fetchCurrencies()
        /**
         * Small delay so the user can actually see the splash screen for a moment as feedback of
         * an attempt to retrieve data.
         */
        delay(timeMillis = 200)
        viewModel?.uiState?.collectLatest { uiState ->
            uiState.isDataRetrievalSuccessful?.let { isDataRetrievalSuccessful ->
                when (isDataRetrievalSuccessful) {
                    true -> onSuccess?.invoke()
                    false -> onFailure?.invoke()
                }
            }
        }
    }
}

@DarkLightPreviews
@Composable
fun SplashScreenPreview() {
    AndroidCurrencyConverterTheme {
        SplashScreen(viewModel = null)
    }
}
