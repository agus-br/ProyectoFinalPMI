package com.example.mynotes.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [NoteTask::class, Reminder::class, MediaFile::class], version = 1, exportSchema = false)
abstract class MyNotesDatabase : RoomDatabase() {
    abstract fun noteTaskDao(): NoteTaskDao
    abstract fun reminderDao(): ReminderDao
    abstract fun mediaFileDao(): MediaFileDao

    companion object {
        @Volatile
        private var INSTANCE: MyNotesDatabase? = null

        fun getDatabase(context: Context): MyNotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyNotesDatabase::class.java,
                    "my_notes_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}