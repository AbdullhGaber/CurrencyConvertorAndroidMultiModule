package com.iti.currency_convertor

object CurrencyConvertor {
    /*
    BASE currency is USD
    EUR to CAD
    USD to EUR = 0.92
    USD to CAD = 1.37

    (1.37 / 0.92) * 10 =
    */

    fun convert(
        fromCurrencyRateVsBaseCurrencyRate: Double,
        toCurrencyRateVsBaseCurrencyRate: Double,
        amount: Double
    ): Double {
        return (toCurrencyRateVsBaseCurrencyRate / fromCurrencyRateVsBaseCurrencyRate) * amount
    }
}