package com.example.mynotes.data

import kotlinx.coroutines.flow.Flow

class OfflineNoteTaskRepository(
    private val noteTaskDao: NoteTaskDao,
    private val reminderDao: ReminderDao,
    private val mediaFileDao: MediaFileDao
) : NoteTaskRepository {

    override fun getAllNoteTasksStream(): Flow<List<NoteTask>> =
        noteTaskDao.getAllNoteTasks()

    override fun getNoteTaskByIdStream(id: Int): Flow<NoteTask?> =
        noteTaskDao.getNoteTask(id)

    override fun getCompletedTaskStream(): Flow<List<NoteTask>> =
        noteTaskDao.getCompleteTasks()

    override fun getUncompletedTaskStream(): Flow<List<NoteTask>> =
        noteTaskDao.getCompleteTasks()

    override suspend fun insertNoteTask(noteTask: NoteTask): Int {
        val noteId = noteTaskDao.insert(noteTask)
        return noteId.toInt()
    }

    override suspend fun deleteNoteTask(noteTask: NoteTask) {
        noteTaskDao.delete(noteTask)
    }

    override suspend fun updateNoteTask(noteTask: NoteTask) {
        noteTaskDao.update(noteTask)
    }

    override fun getRemindersByNoteTaskIdStream(noteTaskId: Int): Flow<List<Reminder>> =
        reminderDao.getRemindersByNoteTaskId(noteTaskId)

    override suspend fun insertReminder(reminder: Reminder) {
        reminderDao.insert(reminder)
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.delete(reminder)
    }

    override suspend fun updateReminder(reminder: Reminder) {
        reminderDao.update(reminder)
    }

    override fun getMediaFilesByNoteTaskIdStream(noteTaskId: Int): Flow<List<MediaFile>> =
        mediaFileDao.getMediaFilesByNoteTaskId(noteTaskId)

    override suspend fun insertMediaFile(mediaFile: MediaFile) {
        mediaFileDao.insert(mediaFile)
    }

    override suspend fun deleteMediaFile(mediaFile: MediaFile) {
        mediaFileDao.delete(mediaFile)
    }
}