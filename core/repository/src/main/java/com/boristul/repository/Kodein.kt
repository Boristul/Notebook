package com.boristul.repository

import android.app.Application
import com.boristul.database.di.databaseKodein
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

fun repositoryModule(application: Application, databaseName: String? = null) = DI.Module(name = "Repository") {
    val db = databaseKodein(application, databaseName)

    bindSingleton { NotesRepository(db.instance()) }
    bindSingleton { TagsRepository(db.instance()) }
    bindSingleton { TaskPointsRepository(db.instance()) }
}
