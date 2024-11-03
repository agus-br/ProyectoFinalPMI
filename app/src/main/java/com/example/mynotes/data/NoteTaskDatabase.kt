package com.example.mynotes.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(
    entities = [NoteTask::class, Reminder::class, MediaFile::class],
    version = 1,
    exportSchema = false)
abstract class MyNotesDatabase : RoomDatabase() {
    abstract fun noteTaskDao(): NoteTaskDao
    abstract fun reminderDao(): ReminderDao
    abstract fun mediaFileDao(): MediaFileDao

    companion object {
        @Volatile
        private var Instance: MyNotesDatabase? = null

        fun getDatabase(context: Context): MyNotesDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    MyNotesDatabase::class.java, "notetask_database"
                )
                    .build().also { Instance = it }
            }
        }
    }
}