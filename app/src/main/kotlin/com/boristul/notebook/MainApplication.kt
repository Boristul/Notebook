package com.boristul.notebook

import android.app.Application
import com.boristul.repository.repositoryModule
import com.boristul.uikit.ThemeSelector
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import org.kodein.di.DI
import org.kodein.di.DIAware

class MainApplication : Application(), DIAware {
    override val di: DI by DI.lazy {
        import(repositoryModule(this@MainApplication, "notebook.db"))
    }

    override fun onCreate() {
        super.onCreate()
        ThemeSelector.initTheme(applicationContext)

        // TODO: Temporary code for demo of firebase statistics
        Firebase.analytics
            .setUserId("0x88a007ec4f1819f24c0988fc9c26496b99b436d10x88a007ec4f1819f24c0988fc9c26496b99b436d1")
    }
}
