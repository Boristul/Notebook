package com.boristul.notebook

import android.app.Application
import com.boristul.repository.repositoryModule
import org.kodein.di.DI
import org.kodein.di.DIAware

class MainApplication : Application(), DIAware {
    override val di: DI by DI.lazy {
        import(repositoryModule(this@MainApplication, "notebook.db"))
    }
}

