package com.boristul.repository

import android.app.Application
import com.boristul.database.di.databaseKodein
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

fun repositoryModule(application: Application, databaseName: String? = null) = DI.Module(name = "Repository") {
    val db = databaseKodein(application, databaseName)

    bind() from singleton { NotesRepository(db.instance()) }
    bind() from singleton { TagsRepository(db.instance()) }
}
