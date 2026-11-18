package com.iti.core.data.di

import android.content.Context
import com.iti.core.data.repository.CurrencyRepository
import com.iti.core.data.repository.CurrencyRepositoryImpl
import com.iti.core.data.worker.SyncManager
import com.iti.core.data.worker.WorkManagerSyncManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WorkManagerModule {
    @Provides
    fun provideSyncManager(@ApplicationContext context : Context) : SyncManager{
        return WorkManagerSyncManager(context)
    }
}