package com.iti.currency_convertor

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.iti.design_system.components.MCTextField
import com.iti.design_system.components.MCTextMenu
import com.iti.design_system.theme.CurrencyConvertorTheme
import kotlin.collections.map

@Composable
fun CurrencyInfoRow(
    modifier: Modifier = Modifier,
    label: String,
    selectedCurrency: CurrencyUiModel,
    currencies: List<CurrencyUiModel>,
    onCurrencyChange: (CurrencyUiModel) -> Unit
) {
    val currencyCodes = remember(currencies) {
        currencies.map { it.code }
    }

    Column {
        Text(text = label, style = MaterialTheme.typography.labelSmall)

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(
                targetState = selectedCurrency.code,
                modifier = Modifier.weight(1f)
            ) {
                MCTextMenu(
                    selectedOption = it,
                    options = currencyCodes,
                    onOptionSelected = { i ->
                        onCurrencyChange(currencies[i].copy(value = selectedCurrency.value))
                    }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            MCTextField(
                value = selectedCurrency.value,
                onValueChange = { onCurrencyChange(selectedCurrency.copy(value = it)) },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 15.dp, vertical = 20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrencyInfoRowPreview() {
    CurrencyConvertorTheme {
        CurrencyInfoRow(
            label = "Amount",
            selectedCurrency = CurrencyUiModel("USD", "100.0"),
            currencies = listOf(
                CurrencyUiModel("USD", "100.0"),
                CurrencyUiModel("EUR", "92.0"),
                CurrencyUiModel("EGP", "4800.0")
            ),
            onCurrencyChange = {}
        )
    }
}