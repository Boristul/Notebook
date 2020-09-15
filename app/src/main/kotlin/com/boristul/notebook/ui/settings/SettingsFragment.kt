package com.boristul.notebook.ui.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.boristul.notebook.R

class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        private const val THEME_PREF = "theme_type_pref"
        private const val DARK_THEME = "Dark"
        private const val LIGHT_THEME = "Light"
        private const val DEFAULT_THEME = "Default"

    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkNotNull(findPreference<ListPreference>(THEME_PREF)).setOnPreferenceChangeListener { _, value ->
            when (value as String) {
                LIGHT_THEME -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                    true
                }
                DARK_THEME -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    true
                }
                DEFAULT_THEME -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

                    true
                }
                else -> false
            }

        }
    }
}
