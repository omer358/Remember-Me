package com.example.rememberme.presentation.onboarding

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.example.rememberme.domain.usecases.people.DeletePersonById
import com.example.rememberme.utils.Constants.NOTIFICATION_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActionReceiver : BroadcastReceiver() {

    @Inject
    lateinit var deletePersonById: DeletePersonById
    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = NotificationManagerCompat.from(context)

        when (intent.action) {
            "ACTION_OKAY" -> {
                // Handle the "Okay" action
                notificationManager.cancel(NOTIFICATION_ID)

            }
            "ACTION_DELETE" -> {
                val personId = intent.getLongExtra("personId",-1)
                GlobalScope.launch {
                    Log.i(TAG,"Deleting the person with id: $personId")
                    deletePersonById(personId)
                    notificationManager.cancel(NOTIFICATION_ID)
                }
            }
        }
    }
    companion object{
        private const val TAG = "NotificationActionRecei"
    }
}
