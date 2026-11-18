package com.iti.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import model.currency_convertor.CurrencyInfo
import model.currency_convertor.ExchangeRates

@Entity(tableName = "exchange_rates")
data class ExchangeRatesEntity(
    @PrimaryKey
    val lastUpdatedDate: String,
    val baseCurrency: String,
    val exchangeRates: Map<String, CurrencyInfo>,
)

fun ExchangeRatesEntity.asExternalModel() = ExchangeRates(
    baseCurrency = baseCurrency,
    rates = exchangeRates.mapValues { it.value.value },
    lastUpdatedDate = lastUpdatedDate
)