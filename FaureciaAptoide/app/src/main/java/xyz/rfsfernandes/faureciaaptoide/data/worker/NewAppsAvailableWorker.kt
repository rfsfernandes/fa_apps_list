package xyz.rfsfernandes.faureciaaptoide.data.worker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import xyz.rfsfernandes.faureciaaptoide.R
import xyz.rfsfernandes.faureciaaptoide.presentation.utils.ui.CHANNEL_ID

class NewAppsAvailableWorker(val context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(context.getString(R.string.new_apps))
            .setContentText(context.getString(R.string.new_apps_description))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED && areNotificationsEnabled()
            ) {
                notify(4, builder)
            }
        }
        return Result.success()
    }
}
