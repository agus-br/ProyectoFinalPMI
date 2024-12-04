package com.example.mynotes

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.mynotes.data.AppContainer
import com.example.mynotes.data.AppDataContainer

class MyNotesApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    companion object {
        private lateinit var instance: MyNotesApplication
        fun getInstance(): MyNotesApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()

        val channelId = "alarm_id"
        val channelName = "alarm_name"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)


        instance = this // Asigna la instancia en onCreate
        container = AppDataContainer(this)
    }
}