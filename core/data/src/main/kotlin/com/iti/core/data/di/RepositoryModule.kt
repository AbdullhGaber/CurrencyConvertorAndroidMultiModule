package com.iti.core.data.di

import com.iti.core.data.repository.CurrencyRepository
import com.iti.core.data.repository.CurrencyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindCurrencyRepository(currencyRepositoryImpl: CurrencyRepositoryImpl) : CurrencyRepository
}