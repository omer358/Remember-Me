package com.omo.rememberme.data.manager

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.omo.rememberme.R
import com.omo.rememberme.domain.model.People
import com.omo.rememberme.presentation.onboarding.NotificationActionReceiver
import com.omo.rememberme.utils.Constants.NOTIFICATION_ID
import com.omo.rememberme.utils.Constants.PEOPLE_CHANNEL_ID
import javax.inject.Inject

class NotificationService @Inject constructor(private val context: Context) {

    companion object {
        private const val TAG = "NotificationService"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notification_channel_name)
            val descriptionText = context.getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(PEOPLE_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(person: People) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("app://people/${person.id}")
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            deepLinkIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val drawable: Drawable? = ContextCompat.getDrawable(context, person.avatar)
        val largeIcon: Bitmap? = drawable?.let { drawableToBitmap(it) }

        // Action for "Okay"
        val okayIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = "ACTION_OKAY"
        }
        val okayPendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            okayIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Action for "Delete"
        val deleteIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = "ACTION_DELETE"
            putExtra("personId", person.id)
        }
        val deletePendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            deleteIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, PEOPLE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(context.getString(R.string.notification_title, person.firstName))
            .setContentText(context.getString(R.string.notification_text, person.place))
            .setLargeIcon(largeIcon)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .addAction(
                R.drawable.baseline_thumb_up_24,
                context.getString(R.string.okay),
                okayPendingIntent
            )
            .addAction(
                R.drawable.baseline_delete_24,
                context.getString(R.string.delete),
                deletePendingIntent
            )
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }


    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else {
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = android.graphics.Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }
}
