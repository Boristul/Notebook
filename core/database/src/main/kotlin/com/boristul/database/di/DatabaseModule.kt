package com.boristul.database.di

import android.content.Context
import androidx.room.Room
import com.boristul.database.DatabaseCallback
import com.boristul.database.NotebookDatabase
import com.boristul.database.dao.NotesDao
import com.boristul.database.dao.TagsDao
import com.boristul.database.dao.TaskPointsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabaseCallback(): DatabaseCallback = DatabaseCallback()

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext applicationContext: Context,
        databaseCallback: DatabaseCallback
    ): NotebookDatabase =
        Room.databaseBuilder(
            applicationContext,
            NotebookDatabase::class.java, "notebook_database"
        )
            .addCallback(databaseCallback)
            .build()

    @Provides
    @Singleton
    fun providesNotesDao(
        notebookDatabase: NotebookDatabase
    ): NotesDao = notebookDatabase.notesDao

    @Provides
    @Singleton
    fun providesTagsDao(
        notebookDatabase: NotebookDatabase
    ): TagsDao = notebookDatabase.tagsDao

    @Provides
    @Singleton
    fun providesTaskPointsDao(
        notebookDatabase: NotebookDatabase
    ): TaskPointsDao = notebookDatabase.taskPointsDao
}
