package com.example.mynotes.notificationSystem

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import com.example.mynotes.utils.Constants

object NotificationChannelManager {

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val reminderChannel = NotificationChannel(
                Constants.REMINDER_CHANNEL_ID,
                "Recordatorios",
                NotificationManager.IMPORTANCE_HIGH
            )
            reminderChannel.description = "Canal para recordatorios"
            reminderChannel.enableLights(true)
            reminderChannel.lightColor = Color.BLUE

            reminderChannel.setShowBadge(true)

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(reminderChannel)
        }
    }
}