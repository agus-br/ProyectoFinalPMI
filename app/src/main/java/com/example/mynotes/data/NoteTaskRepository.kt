package com.example.mynotes.data

import kotlinx.coroutines.flow.Flow

interface NoteTaskRepository {
    /**
     * Retrieve all note tasks as a stream.
     */
    fun getAllNoteTasksStream(): Flow<List<NoteTask>>

    /**
     * Retrieve a specific note task by ID as a stream.
     */
    fun getNoteTaskStream(id: Int): Flow<NoteTask?>

    /**
     * Insert a new note task.
     */
    suspend fun insertNoteTask(noteTask: NoteTask)

    /**
     * Delete a specific note task.
     */
    suspend fun deleteNoteTask(noteTask: NoteTask)

    /**
     * Update an existing note task.
     */
    suspend fun updateNoteTask(noteTask: NoteTask)

    /**
     * Retrieve reminders associated with a specific note task as a stream.
     */
    fun getRemindersForNoteTaskStream(noteTaskId: Int): Flow<List<Reminder>>

    /**
     * Insert a new reminder.
     */
    suspend fun insertReminder(reminder: Reminder)

    /**
     * Delete a specific reminder.
     */
    suspend fun deleteReminder(reminder: Reminder)

    /**
     * Retrieve media files associated with a specific note task as a stream.
     */
    fun getMediaFilesForNoteTaskStream(noteTaskId: Int): Flow<List<MediaFile>>

    /**
     * Insert a new media file.
     */
    suspend fun insertMediaFile(mediaFile: MediaFile)

    /**
     * Delete a specific media file.
     */
    suspend fun deleteMediaFile(mediaFile: MediaFile)
}

