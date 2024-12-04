package com.example.mynotes.notificationSystem

import ReminderInfo
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.mynotes.R
import com.example.mynotes.utils.Constants

object NotificationHelper {

    fun showNotification(context: Context, reminder: ReminderInfo) {
        val builder = NotificationCompat.Builder(context, Constants.REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(reminder.title)
            .setContentText(reminder.description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(reminder.id, builder.build())
    }

}
