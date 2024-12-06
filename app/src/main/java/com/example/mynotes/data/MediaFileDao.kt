package com.example.mynotes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaFileDao {
    @Query("SELECT * FROM media_file WHERE noteTaskId = :noteTaskId")
    fun getMediaFilesByNoteTaskId(noteTaskId: Int): Flow<List<MediaFile>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mediaFile: MediaFile): Long

    @Delete
    suspend fun delete(mediaFile: MediaFile)

    @Query("DELETE FROM media_file WHERE noteTaskId = :noteTaskId")
    suspend fun deleteMediaFilesByNoteTaskId(noteTaskId: Int)
}