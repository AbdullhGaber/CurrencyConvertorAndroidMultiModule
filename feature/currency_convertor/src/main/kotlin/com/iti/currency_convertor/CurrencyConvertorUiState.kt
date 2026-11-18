package com.iti.currency_convertor

data class CurrencyConvertorUiState(
    val isLoading: Boolean = true,
    val allCurrencies: List<CurrencyUiModel> = emptyList(),
    val fromCurrency: CurrencyUiModel = CurrencyUiModel("", ""),
    val toCurrency: CurrencyUiModel = CurrencyUiModel("", ""),
    val indicativeExchangeRate: String = "",
    val lastUpdated: String = "",
){
    companion object {
        val PreviewData = CurrencyConvertorUiState(
            fromCurrency = CurrencyUiModel("USD", "1000.00"),
            toCurrency = CurrencyUiModel("USD", "321.00"),
            indicativeExchangeRate = "1 USD = 1 USD"
        )
    }
}