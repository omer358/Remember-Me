package com.example.rememberme.data.manager


import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.rememberme.domain.usecases.people.GetAllPeople
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

@HiltWorker
class NotificationWorker
    @AssistedInject constructor(
        @Assisted context: Context,
        @Assisted workerParams: WorkerParameters,
        private val getAllPeople: GetAllPeople,
        private val notificationService: NotificationService
    ) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d(TAG, "Performing long running task in scheduled job")
        return runBlocking {
            getAllPeople().collect { people ->
                if (people.isNotEmpty()) {
                    Log.d(TAG, "People: $people")
                    val randomPerson = people[Random.nextInt(people.size)]
                    notificationService.showNotification(randomPerson)
                } else {
                    Log.d(TAG, "No people found")
                }
            }
            Log.d(TAG, "Work finished")
            Result.success()
        }
    }
    companion object {
        private const val TAG = "NotificationWorker"
    }
}
