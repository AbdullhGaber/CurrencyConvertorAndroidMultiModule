package com.iti.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.iti.database.convertor.CurrencyRatesConvertor
import com.iti.database.dao.CurrencyDao
import com.iti.database.model.ExchangeRatesEntity

@Database(
    entities = [ExchangeRatesEntity::class],
    version = 1
)
@TypeConverters(CurrencyRatesConvertor::class)
abstract class CurrencyDatabase: RoomDatabase() {

    abstract fun dao(): CurrencyDao

    companion object {
        const val NAME = "currency_db"
    }
}