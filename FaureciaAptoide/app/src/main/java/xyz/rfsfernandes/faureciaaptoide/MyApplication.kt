package xyz.rfsfernandes.faureciaaptoide

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp
import xyz.rfsfernandes.faureciaaptoide.data.worker.NewAppsAvailableWorkerWrapper
import xyz.rfsfernandes.faureciaaptoide.presentation.utils.ui.CHANNEL_ID

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        createNotificationChannel()
        setupWorker()
    }

    private fun setupWorker() {
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            getString(R.string.newAppsWorkerName),
            ExistingPeriodicWorkPolicy.KEEP,
            NewAppsAvailableWorkerWrapper.periodicWorkRequest
        )
    }

    private fun createNotificationChannel() = with(this) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        val name = getString(R.string.channel_name)
        val descriptionText =
            getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system.
        val notificationManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
