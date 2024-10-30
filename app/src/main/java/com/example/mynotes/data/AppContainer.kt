package com.example.mynotes.data

import android.content.Context

interface AppContainer {
    val noteTaskRepository: NoteTaskRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val noteTaskRepository: NoteTaskRepository by lazy {
        val database = MyNotesDatabase.getDatabase(context)
        DefNoteTaskRepository(
            noteTaskDao = database.noteTaskDao(),
            reminderDao = database.reminderDao(),
            mediaFileDao = database.mediaFileDao()
        )
    }
}