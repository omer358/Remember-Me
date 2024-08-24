package com.omo.rememberme.data.manager


import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.omo.rememberme.domain.usecases.people.GetAllPeople
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.random.Random

@HiltWorker
class NotificationWorker
    @AssistedInject constructor(
        @Assisted context: Context,
        @Assisted workerParams: WorkerParameters,
        private val getAllPeople: GetAllPeople,
        private val notificationService: NotificationService
    ) : CoroutineWorker(context, workerParams) {


    override suspend fun doWork(): Result {
        Log.d(TAG, "Performing long running task in scheduled job")
        return try {
            // Collect the most recent data within a limited time frame
            val people = withTimeoutOrNull(TIMEOUT_MILLIS) {
                getAllPeople().first { it.isNotEmpty() } // Wait until at least one person is available
            }

            if (!people.isNullOrEmpty()) {
                Log.d(TAG, "People: $people")
                val randomPerson = people[Random.nextInt(people.size)]
                notificationService.showNotification(randomPerson)
            } else {
                Log.d(TAG, "No people found")
            }
            Log.d(TAG, "Work finished")
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error during work", e)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "NotificationWorker"
        private const val TIMEOUT_MILLIS = 5000L // 5 seconds timeout to get the latest data
    }
}