package com.example.mynotes.notificationSystem

import ReminderInfo
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver
    : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val reminderId = intent.getIntExtra("reminder_id", 0)
        val reminderTitle = intent.getStringExtra("reminder_title") ?: ""
        val reminderDescription = intent.getStringExtra("reminder_description") ?: ""
        val reminderTime = intent.getLongExtra("reminder_time", 0L)

        val reminder = ReminderInfo(
            id = reminderId,
            title = reminderTitle,
            description = reminderDescription,
            timeInMillis = reminderTime
        )

        if (reminder.id != 0) {
            NotificationHelper.showNotification(context, reminder)
        }
    }
}