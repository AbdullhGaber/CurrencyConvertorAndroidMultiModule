package com.iti.core.data.worker

import Synchronizer
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.iti.core.data.repository.CurrencyRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val context : Context,
    @Assisted private val workerParams : WorkerParameters,
    private val currencyRepository: CurrencyRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try{
            currencyRepository.syncWith(Synchronizer())
            return Result.success()
        }catch (e: Exception){
            Result.failure()
        }
    }

    companion object {
        const val NAME = "SyncWorker"
        val REQUEST = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(WorkerUtils.DEFAULT_CONSTRAINTS)
            .build()
    }

}