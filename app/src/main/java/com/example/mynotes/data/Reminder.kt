package com.example.mynotes.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "reminder",
    foreignKeys = [ForeignKey(entity = NoteTask::class, parentColumns = ["id"], childColumns = ["noteTaskId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("noteTaskId")]
)
data class Reminder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var noteTaskId: Int,
    val isActive: Boolean,
    val dateInMillis: Long? = null,
    val timeInMillis: Long
)
