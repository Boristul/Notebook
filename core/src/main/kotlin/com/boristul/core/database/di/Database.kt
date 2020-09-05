package com.boristul.core.database.di

import android.app.Application
import androidx.room.Room
import com.boristul.core.database.NotebookDatabase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

fun databaseKodein(application: Application, databaseName: String? = null) = DI.direct {
    bind() from singleton {
        val builder =
            if (databaseName == null) Room.inMemoryDatabaseBuilder(application, NotebookDatabase::class.java)
            else Room.databaseBuilder(application, NotebookDatabase::class.java, databaseName)
        builder.build()
    }

    bind() from provider { instance<NotebookDatabase>().notesDao }
}