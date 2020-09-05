package com.boristul.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.boristul.core.database.dao.NotesDao
import com.boristul.core.database.entity.NoteEntity

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
