package com.iti.testing.worker

import Synchronizer
import com.iti.core.data.repository.CurrencyRepository
import com.iti.core.data.worker.SyncManager
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import model.currency_convertor.ExchangeRates

class TestWorkManagerSyncManager : SyncManager {
    private val syncingFlow: MutableSharedFlow<Boolean> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override val isSyncing: Flow<Boolean>
        get() = syncingFlow

    fun emit(isSyncing: Boolean) {
        syncingFlow.tryEmit(isSyncing)
    }
}