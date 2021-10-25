package com.boristul.database.di

import android.app.Application
import androidx.room.Room
import com.boristul.database.DatabaseCallback
import com.boristul.database.NotebookDatabase
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

fun databaseKodein(application: Application, databaseName: String? = null) = DI.direct {
    bindSingleton {
        val builder =
            if (databaseName == null) Room.inMemoryDatabaseBuilder(application, NotebookDatabase::class.java)
            else Room.databaseBuilder(application, NotebookDatabase::class.java, databaseName).addCallback(DatabaseCallback())
        builder.build()
    }

    bindSingleton { instance<NotebookDatabase>().notesDao }
    bindSingleton { instance<NotebookDatabase>().tagsDao }
    bindSingleton { instance<NotebookDatabase>().taskPointsDao }
}
