package com.example.mynotes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteTaskDao {

    @Query("SELECT * FROM note_task ORDER BY dueDate ASC")
    fun getAllNoteTasks(): Flow<List<NoteTask>>

    @Query("SELECT * FROM note_task WHERE id = :id")
    fun getNoteTask(id: Int): Flow<NoteTask>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteTask: NoteTask)

    @Delete
    suspend fun delete(noteTask: NoteTask)

    @Update
    suspend fun update(noteTask: NoteTask)

    // Obtener todas las tareas incompletas en tiempo real
    @Query("SELECT * FROM note_task WHERE type = :type AND isCompleted = 0")
    fun getUncompleteTasks(type: NoteTaskType = NoteTaskType.TASK): Flow<List<NoteTask>>

    // Obtener todas las tareas incompletas en tiempo real
    @Query("SELECT * FROM note_task WHERE type = :type AND isCompleted = 1")
    fun getCompleteTasks(type: NoteTaskType = NoteTaskType.TASK): Flow<List<NoteTask>>

    // Obtener todas las tareas en tiempo real
    @Query("SELECT * FROM note_task WHERE type = :type")
    fun getAllTasks(type: NoteTaskType = NoteTaskType.TASK): Flow<List<NoteTask>>

    // Obtener todas las notas en tiempo real
    @Query("SELECT * FROM note_task WHERE type = :type")
    fun getAllNotes(type: NoteTaskType = NoteTaskType.NOTE): Flow<List<NoteTask>>
}
