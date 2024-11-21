package com.example.mynotes

import android.app.Application
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
        instance = this // Asigna la instancia en onCreate
        container = AppDataContainer(this)
    }
}