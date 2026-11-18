package com.iti.core.data.worker

import androidx.work.Constraints
import androidx.work.NetworkType

object WorkerUtils {
    val DEFAULT_CONSTRAINTS = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
}