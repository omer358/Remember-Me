package com.example.rememberme

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.rememberme.data.manager.NotificationWorker
import com.example.rememberme.domain.model.RemindersRepetition
import com.example.rememberme.presentation.settings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        lifecycleScope.launch {
            settingsViewModel.uiState.collect { uiState ->
                if (isGranted) {
                    // Permission is granted. Schedule the notification work with the correct repetition.
                    Log.i(TAG, "Permission granted")
                    scheduleNotificationWork(uiState.remindersRepetition)
                } else {
                    Log.i(TAG, "Permission denied")
                    // Permission is denied. Handle the case.
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()

        setContent {
            RememberMeApp()
        }

        lifecycleScope.launch {
            settingsViewModel.uiState.collect { uiState ->
                val remindersRepetition = uiState.remindersRepetition

                // Check for notification permission only if the SDK version is 33 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        // Permission is already granted
                        scheduleNotificationWork(remindersRepetition)
                    } else {
                        // Request permission
                        requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                    }
                } else {
                    // SDK version is lower than 33, no need to request POST_NOTIFICATIONS permission
                    scheduleNotificationWork(remindersRepetition)
                }
            }
        }
    }

    private fun scheduleNotificationWork(repetition: RemindersRepetition) {
        Log.i(TAG, "Scheduling notification work with repetition: $repetition")
        val repeatInterval = when (repetition) {
            RemindersRepetition.OnceADay -> 24
            RemindersRepetition.ThreeADay -> 24 /3
            RemindersRepetition.FiveADay -> 24 / 5
        }

        Log.i(TAG, "Scheduling notification work with interval: $repeatInterval hours")
        val notificationWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            repeatInterval.toLong(),
            TimeUnit.HOURS)
            .addTag("notificationWorkRequest")
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "notificationWork",
            ExistingPeriodicWorkPolicy.KEEP, // Keeps the existing work if it exists
            notificationWorkRequest
        )
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}