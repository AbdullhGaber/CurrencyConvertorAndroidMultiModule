package com.iti.currency_convertor

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.design_system.components.MCCard
import com.iti.design_system.R
import com.iti.design_system.theme.CurrencyConvertorTheme

@Composable
fun CurrencyConvertorCard(
    modifier: Modifier = Modifier,
    allCurrencies: List<CurrencyUiModel>,
    fromCurrency: CurrencyUiModel,
    toCurrency: CurrencyUiModel,
    onFromCurrencyChanged: (CurrencyUiModel) -> Unit,
    onToCurrencyChanged: (CurrencyUiModel) -> Unit,
    swapCurrencies: () -> Unit
) {
    MCCard(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        CurrencyInfoRow(
            label = stringResource(id = R.string.amount),
            selectedCurrency = fromCurrency,
            currencies = allCurrencies,
            onCurrencyChange = onFromCurrencyChanged
        )

        Spacer(modifier = Modifier.height(20.dp))
        CurrenciesSwapper(
            onSwap = swapCurrencies
        )
        Spacer(modifier = Modifier.height(10.dp))

        CurrencyInfoRow(
            label = stringResource(id = R.string.converted_amount),
            selectedCurrency = toCurrency,
            currencies = allCurrencies,
            onCurrencyChange = onToCurrencyChanged
        )
    }
}

@Preview
@Composable
private fun CurrencyConvertorCardPreview() {
    CurrencyConvertorTheme {
        CurrencyConvertorCard(
            allCurrencies = listOf(
                CurrencyUiModel("USD", "100.0"),
                CurrencyUiModel("EUR", "92.0"),
                CurrencyUiModel("EGP", "4800.0")
            ),
            fromCurrency = CurrencyUiModel("USD", "100.0"),
            toCurrency = CurrencyUiModel("EUR", "92.0"),
            onFromCurrencyChanged = {},
            onToCurrencyChanged = {},
            swapCurrencies = {}
        )
    }
}