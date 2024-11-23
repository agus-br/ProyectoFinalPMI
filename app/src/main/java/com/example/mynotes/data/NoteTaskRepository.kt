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
    fun getNoteTaskByIdStream(id: Int): Flow<NoteTask?>

    /**
     * Retrieve a task completed list as a stream.
     */
    fun getCompletedTaskStream(): Flow<List<NoteTask>>

    /**
     *  Retrieve a task uncompleted list as a stream.
     */
    fun getUncompletedTaskStream(): Flow<List<NoteTask>>

    /**
     * Insert a new note task.
     */
    suspend fun insertNoteTask(noteTask: NoteTask): Int

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
    fun getRemindersByNoteTaskIdStream(noteTaskId: Int): Flow<List<Reminder>>

    /**
     * Insert a new reminder.
     */
    suspend fun insertReminder(reminder: Reminder)

    /**
     * Delete a specific reminder.
     */
    suspend fun deleteReminder(reminder: Reminder)

    /**
     * Update a specific reminder.
     */
    suspend fun updateReminder(reminder: Reminder)

    /**
     * Retrieve media files associated with a specific note task as a stream.
     */
    fun getMediaFilesByNoteTaskIdStream(noteTaskId: Int): Flow<List<MediaFile>>

    /**
     * Insert a new media file.
     */
    suspend fun insertMediaFile(mediaFile: MediaFile)

    /**
     * Delete a specific media file.
     */
    suspend fun deleteMediaFile(mediaFile: MediaFile)
}

