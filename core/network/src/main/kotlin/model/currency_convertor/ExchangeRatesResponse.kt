package model.currency_convertor

import kotlinx.serialization.Serializable
import kotlin.collections.mapValues

@Serializable
data class ExchangeRatesResponse(
    val meta: Meta,
    val data: Map<String, CurrencyInfo>
)


fun ExchangeRatesResponse.toExternalModel(baseCurrency: String): ExchangeRates {
    return ExchangeRates(
        baseCurrency = baseCurrency,
        rates = data.mapValues { it.value.value },
        lastUpdatedDate = meta.lastUpdatedAt
    )
}