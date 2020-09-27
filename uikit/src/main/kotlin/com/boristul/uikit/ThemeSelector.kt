package com.boristul.uikit

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

object ThemeSelector {
        private const val THEME_PREF = "theme_type_pref"
        private const val DEFAULT_STRING = "Default"

        // TODO: this is because of list pref use only string-arrays
        fun initTheme(context: Context) {
                PreferenceManager.getDefaultSharedPreferences(context).getString(
                        THEME_PREF,
                        DEFAULT_STRING
                )?.also {
                        AppCompatDelegate.setDefaultNightMode(
                                if (it == DEFAULT_STRING) AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM else it.toInt()
                        )
                }
        }

        fun setTheme(themeMode: Int) {
                AppCompatDelegate.setDefaultNightMode(themeMode)
        }
}
