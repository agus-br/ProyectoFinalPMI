package com.example.mynotes.data

import kotlinx.coroutines.flow.Flow

class DefNoteTaskRepository(
    private val noteTaskDao: NoteTaskDao,
    private val reminderDao: ReminderDao,
    private val mediaFileDao: MediaFileDao
) : NoteTaskRepository {

    override fun getAllNoteTasksStream(): Flow<List<NoteTask>> =
        noteTaskDao.getAllNoteTasks() // Este método permanece igual

    override fun getNoteTaskStream(id: Int): Flow<NoteTask?> =
        noteTaskDao.getNoteTask(id) // Este método permanece igual

    override suspend fun insertNoteTask(noteTask: NoteTask) {
        noteTaskDao.insert(noteTask) // Este método permanece igual
    }

    override suspend fun deleteNoteTask(noteTask: NoteTask) {
        noteTaskDao.delete(noteTask) // Este método permanece igual
    }

    override suspend fun updateNoteTask(noteTask: NoteTask) {
        noteTaskDao.update(noteTask) // Este método permanece igual
    }

    override fun getRemindersForNoteTaskStream(noteTaskId: Int): Flow<List<Reminder>> =
        reminderDao.getRemindersByNoteTaskId(noteTaskId) // Cambia el nombre del método

    override suspend fun insertReminder(reminder: Reminder) {
        reminderDao.insert(reminder) // Este método permanece igual
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.delete(reminder) // Este método permanece igual
    }

    override fun getMediaFilesForNoteTaskStream(noteTaskId: Int): Flow<List<MediaFile>> =
        mediaFileDao.getMediaFilesByNoteTaskId(noteTaskId) // Cambia el nombre del método

    override suspend fun insertMediaFile(mediaFile: MediaFile) {
        mediaFileDao.insert(mediaFile) // Este método permanece igual
    }

    override suspend fun deleteMediaFile(mediaFile: MediaFile) {
        mediaFileDao.delete(mediaFile) // Este método permanece igual
    }
}