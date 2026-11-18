package com.iti.currencyconvertor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.iti.design_system.theme.CurrencyConvertorTheme
import com.iti.currencyconvertor.navigation.MoneyConvertorNavGraph
import com.iti.database.db.CurrencyDatabase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var currencyDatabase: CurrencyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CurrencyConvertorTheme {
                MoneyConvertorNavGraph()
            }
        }
    }
}