package com.iti.currency_convertor.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.iti.currency_convertor.CurrencyConvertorRoute
import com.iti.currency_convertor.navigation.CurrencyConvertorRoute

const val CurrencyConvertorRoute = "currencyConvertorRoute"

fun NavGraphBuilder.currencyConvertorScreen() {
    composable(CurrencyConvertorRoute) {
        CurrencyConvertorRoute()
    }
}