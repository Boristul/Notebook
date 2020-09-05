package com.boristul.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.boristul.database.dao.NotesDao
import com.boristul.database.entity.NoteEntity

@Database(
    entities = [
        NoteEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
internal abstract class NotebookDatabase : RoomDatabase() {
    abstract val notesDao: NotesDao
}
