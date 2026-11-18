package com.iti.database.convertor

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import model.currency_convertor.CurrencyInfo


class CurrencyRatesConvertor {

    @TypeConverter
    fun fromCurrenciesMapToString(currencies: Map<String, CurrencyInfo>): String {
        var result = ""
        currencies.forEach {
            result += "${it.key}:${it.value.code},${it.value.value}/"
        }
        return result
    }

    @TypeConverter
    fun fromCurrenciesStringToMap(string: String): Map<String, CurrencyInfo> {
        val result = mutableMapOf<String, CurrencyInfo>()
        val mapEntries = string.split("/")
        for (mapEntry in mapEntries) {
            val keyValue = mapEntry.split(":")
            val key = keyValue.firstOrNull() ?: break
            val value = keyValue.getOrNull(1)?.split(",") ?: break
            val currencyInfo = CurrencyInfo(value.first(), value.last().toDouble())
            result[key] = currencyInfo
        }
        return result
    }
}