package com.iti.currency_convertor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iti.design_system.components.MCBackgroundScreen
import com.iti.design_system.R
import com.iti.design_system.theme.CurrencyConvertorTheme
import kotlin.collections.listOf

@Composable
internal fun CurrencyConvertorRoute(
    viewModel: CurrencyViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CurrencyConvertorScreen(
        uiState = uiState,
        onFromCurrencyChange = viewModel::onFromCurrencyChange,
        onToCurrencyChange = viewModel::onToCurrencyChange,
        onSwap = viewModel::swapCurrencies
    )
}

@Composable
internal fun CurrencyConvertorScreen(
    uiState: CurrencyConvertorUiState,
    onFromCurrencyChange: (CurrencyUiModel) -> Unit,
    onToCurrencyChange: (CurrencyUiModel) -> Unit,
    onSwap: () -> Unit
) {
    MCBackgroundScreen {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = stringResource(id = R.string.currency_convertor),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.currency_convertor_description),
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                color = Color(0xff808080),
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(50.dp))

            CurrencyConvertorCard(
                allCurrencies = uiState.allCurrencies,
                fromCurrency = uiState.fromCurrency,
                toCurrency = uiState.toCurrency,
                onFromCurrencyChanged = onFromCurrencyChange,
                onToCurrencyChanged = onToCurrencyChange,
                swapCurrencies = onSwap
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "${stringResource(id = R.string.indicative_exchange_rate)} ${uiState.lastUpdated}",
                modifier = Modifier.padding(horizontal = 22.dp),
                style = MaterialTheme.typography.labelSmall
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = uiState.indicativeExchangeRate,
                modifier = Modifier.padding(horizontal = 22.dp),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Black
                )
            )

            if(uiState.isLoading){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(30.dp).testTag("Loading")
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMCBackgroundScreen() {
    CurrencyConvertorTheme {
        CurrencyConvertorScreen(
            uiState = CurrencyConvertorUiState.PreviewData,
            onFromCurrencyChange = {},
            onToCurrencyChange = {},
            onSwap = {}
        )
    }
}