package com.example.mynotes.data

import kotlinx.coroutines.flow.Flow

class DefNoteTaskRepository(
    private val noteTaskDao: NoteTaskDao,
    private val reminderDao: ReminderDao,
    private val mediaFileDao: MediaFileDao
) : NoteTaskRepository {

    override fun getAllNoteTasksStream(): Flow<List<NoteTask>> =
        noteTaskDao.getAllNoteTasks()

    override fun getNoteTaskStream(id: Int): Flow<NoteTask?> =
        noteTaskDao.getNoteTask(id)

    override suspend fun insertNoteTask(noteTask: NoteTask) {
        noteTaskDao.insert(noteTask)
    }

    override suspend fun deleteNoteTask(noteTask: NoteTask) {
        noteTaskDao.delete(noteTask)
    }

    override suspend fun updateNoteTask(noteTask: NoteTask) {
        noteTaskDao.update(noteTask)
    }

    override fun getRemindersForNoteTaskStream(noteTaskId: Int): Flow<List<Reminder>> =
        reminderDao.getRemindersByNoteTaskId(noteTaskId)

    override suspend fun insertReminder(reminder: Reminder) {
        reminderDao.insert(reminder)
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.delete(reminder)
    }

    override fun getMediaFilesForNoteTaskStream(noteTaskId: Int): Flow<List<MediaFile>> =
        mediaFileDao.getMediaFilesByNoteTaskId(noteTaskId)

    override suspend fun insertMediaFile(mediaFile: MediaFile) {
        mediaFileDao.insert(mediaFile)
    }

    override suspend fun deleteMediaFile(mediaFile: MediaFile) {
        mediaFileDao.delete(mediaFile)
    }
}