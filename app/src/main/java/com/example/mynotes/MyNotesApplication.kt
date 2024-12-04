package com.example.mynotes

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.mynotes.data.AppContainer
import com.example.mynotes.data.AppDataContainer
import com.example.mynotes.notificationSystem.NotificationChannelManager
import com.example.mynotes.utils.Constants

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

        NotificationChannelManager.createNotificationChannels(this)

        instance = this // Asigna la instancia en onCreate
        container = AppDataContainer(this)
    }
}