package com.iti.core.data.mapper

import com.iti.database.model.ExchangeRatesEntity
import model.currency_convertor.ExchangeRatesResponse

fun ExchangeRatesResponse.toEntity(baseCurrencyCode: String): ExchangeRatesEntity {
    return ExchangeRatesEntity(
        baseCurrency = baseCurrencyCode,
        lastUpdatedDate = meta.lastUpdatedAt,
        exchangeRates = data
    )
}