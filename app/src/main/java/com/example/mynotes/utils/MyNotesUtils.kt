package com.example.mynotes.utils

import android.app.Application
import com.example.mynotes.MyNotesApplication

fun myNotesApplication(): MyNotesApplication {
    return MyNotesApplication.getInstance() // Accede a la instancia de la aplicación
}