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
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.rememberme.data.manager.NotificationWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    @Inject
//    lateinit var peopleDao: PeopleDao

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Schedule the notification work.
            Log.i(TAG, "Permission granted")
            scheduleNotificationWork()
        } else {
            Log.i(TAG, "Permission denied")
            // Permission is denied. Handle the case.
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()

//        lifecycleScope.launch {
//            withContext(Dispatchers.IO){
//                peopleDao.insertAll(FakeDataSource.getPeopleList())
//            }
//        }
        setContent {
            RememberMeApp()
        }

        // Check for notification permission only if the SDK version is 33 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is already granted
                scheduleNotificationWork()
            } else {
                // Request permission
                requestPermissionLauncher.launch(POST_NOTIFICATIONS)
            }
        } else {
            // SDK version is lower than 33, no need to request POST_NOTIFICATIONS permission
            scheduleNotificationWork()
        }
    }
    private fun scheduleNotificationWork() {
        // Schedule the notification
        val notificationWorkRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
                .addTag("notificationWork")
                .setInitialDelay(5, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(this).enqueue(notificationWorkRequest)
    }
    companion object {
        private const val TAG = "MainActivity"
    }

}