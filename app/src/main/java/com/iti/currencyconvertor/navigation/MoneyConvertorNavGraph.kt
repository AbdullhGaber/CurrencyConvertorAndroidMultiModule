package com.iti.currencyconvertor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.iti.currency_convertor.navigation.CurrencyConvertorRoute
import com.iti.currency_convertor.navigation.currencyConvertorScreen

@Composable
fun MoneyConvertorNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = CurrencyConvertorRoute) {
        currencyConvertorScreen()
    }
}