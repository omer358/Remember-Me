package com.omo.rememberme

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
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.omo.rememberme.data.manager.NotificationWorker
import com.omo.rememberme.domain.model.RemindersRepetition
import com.omo.rememberme.domain.usecases.app_entry.AppEntryUseCases
import com.omo.rememberme.presentation.settings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var appEntryUseCases: AppEntryUseCases

    private var lastRepetition: RemindersRepetition? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        handlePermissionResult(isGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI(viewModel = mainViewModel)
        observeUiState()
    }

    private fun setupUI(viewModel: MainViewModel) {
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.splashCondition
            }
        }
        setContent { RememberMeApp(viewModel.startDestination) }
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            settingsViewModel.uiState.collect { uiState ->
                if (shouldScheduleWork(uiState.remindersRepetition)) {
                    handlePermissionsAndScheduleWork(uiState.remindersRepetition)
                }
            }
        }
    }

    private fun shouldScheduleWork(currentRepetition: RemindersRepetition): Boolean {
        return currentRepetition != lastRepetition
    }

    private fun handlePermissionsAndScheduleWork(repetition: RemindersRepetition) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (hasNotificationPermission()) {
                scheduleNotificationWork(repetition)
            } else {
                requestNotificationPermission()
            }
        } else {
            scheduleNotificationWork(repetition)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun hasNotificationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        requestPermissionLauncher.launch(POST_NOTIFICATIONS)
    }

    private fun handlePermissionResult(isGranted: Boolean) {
        lifecycleScope.launch {
            settingsViewModel.uiState.collect { uiState ->
                if (isGranted && shouldScheduleWork(uiState.remindersRepetition)) {
                    scheduleNotificationWork(uiState.remindersRepetition)
                }
            }
        }
    }

    private fun scheduleNotificationWork(repetition: RemindersRepetition) {
        Log.i(TAG, "Scheduling notification work with repetition: $repetition")
        val repeatInterval = getRepeatIntervalInHours(repetition).toLong()

        Log.i(TAG, "Scheduling notification work with interval: $repeatInterval hours")
        val notificationWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            repeatInterval, TimeUnit.HOURS
        ).addTag("notificationWorkRequest")
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "notificationWork",
            ExistingPeriodicWorkPolicy.UPDATE,
            notificationWorkRequest
        )
        lastRepetition = repetition
    }

    private fun getRepeatIntervalInHours(repetition: RemindersRepetition): Int {
        return when (repetition) {
            RemindersRepetition.OnceADay -> 24
            RemindersRepetition.ThreeADay -> 24 / 3
            RemindersRepetition.FiveADay -> 24 / 5
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}

