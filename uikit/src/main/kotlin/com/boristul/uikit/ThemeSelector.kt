package com.boristul.uikit

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

object ThemeSelector {
    private const val THEME_PREF = "theme_type_pref"
    private const val DEFAULT_VALUE = "-1"

    // TODO: this is because of list pref use only string-arrays
    fun initTheme(context: Context) {
        PreferenceManager.getDefaultSharedPreferences(context).getString(
            THEME_PREF,
            DEFAULT_VALUE
        )?.also {
            AppCompatDelegate.setDefaultNightMode(
                if (it == DEFAULT_VALUE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    } else {
                        AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                    }
                } else {
                    it.toInt()
                }
            )
        }
    }

    fun setTheme(themeMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }
}
