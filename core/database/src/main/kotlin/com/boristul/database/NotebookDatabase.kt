package com.boristul.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.boristul.database.dao.NotesDao
import com.boristul.database.dao.TagsDao
import com.boristul.database.dao.TaskPointsDao
import com.boristul.database.entity.NoteEntity
import com.boristul.database.entity.NoteTagCrossRef
import com.boristul.database.entity.TagEntity
import com.boristul.database.entity.TaskPointEntity

@Database(
    entities = [
        NoteEntity::class,
        TagEntity::class,
        NoteTagCrossRef::class,
        TaskPointEntity::class,
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class NotebookDatabase : RoomDatabase() {
    abstract val notesDao: NotesDao
    abstract val tagsDao: TagsDao
    abstract val taskPointsDao: TaskPointsDao
}
