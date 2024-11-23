package com.example.mynotes.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "media_file",
    foreignKeys = [ForeignKey(entity = NoteTask::class, parentColumns = ["id"], childColumns = ["noteTaskId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("noteTaskId")]
)
data class MediaFile(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var noteTaskId: Int,
    val filePath: String,
    val mediaType: MediaType // Indica si es imagen, video o audio
)

enum class MediaType {
    IMAGE,
    VIDEO,
    AUDIO
}
