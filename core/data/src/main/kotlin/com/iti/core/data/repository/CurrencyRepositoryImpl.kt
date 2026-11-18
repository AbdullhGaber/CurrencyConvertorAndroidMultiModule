package com.iti.core.data.repository

import Synchronizer
import com.iti.core.data.mapper.toEntity
import com.iti.database.db.CurrencyDatabase
import com.iti.database.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import model.currency_convertor.ExchangeRates
import model.currency_convertor.toExternalModel
import service.CurrencyService
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
     db : CurrencyDatabase,
     private val currencyService : CurrencyService
) : CurrencyRepository {
     val currencyDao = db.dao()

     override fun getExchangeRates(): Flow<ExchangeRates> {
          return currencyDao.getExchangeRates().map { it.asExternalModel() }
     }

     override suspend fun syncWith(synchronizer: Synchronizer) {
          synchronizer.start(
               fetchRemoteData = { currencyService.getExchangeRates() },
               deleteLocalData = { currencyDao.clearExchangeRates() },
               updateLocalData = { currencyDao.upsertExchangeRates(it.toEntity("USD")) }
          )
     }
}