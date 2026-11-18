package com.iti.core.data.repository

import Syncable
import kotlinx.coroutines.flow.Flow
import model.currency_convertor.ExchangeRates

interface CurrencyRepository : Syncable{
     fun getExchangeRates() : Flow<ExchangeRates>
}