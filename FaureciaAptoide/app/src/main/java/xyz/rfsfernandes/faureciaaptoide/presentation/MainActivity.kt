package xyz.rfsfernandes.faureciaaptoide.presentation

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import xyz.rfsfernandes.faureciaaptoide.R
import xyz.rfsfernandes.faureciaaptoide.data.worker.NewAppsAvailableWorkerWrapper
import xyz.rfsfernandes.faureciaaptoide.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        askPermissions()
    }

    private fun askPermissions() {
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) { // only if user grants access to notifications the worker is triggered
                    enqueueWorker()
                }
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // only TIRAMISU requires a notification requests
            requestPermissionLauncher.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
            enqueueWorker()
        }
    }

    private fun enqueueWorker() {
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            getString(R.string.newAppsWorkerName),
            ExistingPeriodicWorkPolicy.KEEP,
            NewAppsAvailableWorkerWrapper.periodicWorkRequest
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
