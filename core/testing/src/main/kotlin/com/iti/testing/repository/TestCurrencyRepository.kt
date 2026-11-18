package com.iti.testing.repository

import Synchronizer
import com.iti.core.data.repository.CurrencyRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import model.currency_convertor.ExchangeRates

class TestCurrencyRepository : CurrencyRepository {

    private val exchangeRates: MutableSharedFlow<ExchangeRates> = MutableSharedFlow(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override fun getExchangeRates(): Flow<ExchangeRates> {
        return exchangeRates
    }

    fun sendExchangeRates(exchangeRates: ExchangeRates){
        this.exchangeRates.tryEmit(exchangeRates)
    }

    override suspend fun syncWith(synchronizer: Synchronizer) {}
}