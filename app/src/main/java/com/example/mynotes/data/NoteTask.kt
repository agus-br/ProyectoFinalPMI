package com.example.mynotes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_task")
data class NoteTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    @ColumnInfo(name = "type") val type: NoteTaskType,
    @ColumnInfo(name = "due_date") val dueDate: Long? = null, // Solo para tareas
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean = false, // Solo para tareas
)

enum class NoteTaskType {
    NOTE,
    TASK
}