package com.boristul.notebook

import android.app.Application
import com.boristul.uikit.ThemeSelector
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ThemeSelector.initTheme(applicationContext)
    }
}
