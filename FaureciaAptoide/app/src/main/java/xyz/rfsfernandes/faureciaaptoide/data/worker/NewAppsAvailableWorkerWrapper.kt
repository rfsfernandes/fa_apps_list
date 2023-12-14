package xyz.rfsfernandes.faureciaaptoide.data.worker

import androidx.work.PeriodicWorkRequestBuilder
import java.util.concurrent.TimeUnit

object NewAppsAvailableWorkerWrapper {
    val periodicWorkRequest =
        PeriodicWorkRequestBuilder<NewAppsAvailableWorker>(30, TimeUnit.MINUTES).build()
}
