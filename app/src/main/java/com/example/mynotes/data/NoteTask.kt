package com.example.mynotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_task")
data class NoteTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val type: NoteTaskType,
    val dueDate: Long? = null, // Solo para tareas
    val isCompleted: Boolean = false, // Solo para tareas
) {
    fun isNote(): Boolean = type == NoteTaskType.NOTE

    fun isTask(): Boolean = type == NoteTaskType.TASK
}

enum class NoteTaskType {
    NOTE,
    TASK
}