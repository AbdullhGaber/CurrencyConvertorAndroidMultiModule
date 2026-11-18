package com.iti.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.iti.database.model.ExchangeRatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Upsert
    suspend fun upsertExchangeRates(currencies: ExchangeRatesEntity)

    @Query("DELETE FROM `exchange_rates`")
    suspend fun clearExchangeRates()

    @Query("SELECT * FROM `exchange_rates`")
    fun getExchangeRates(): Flow<ExchangeRatesEntity>
}