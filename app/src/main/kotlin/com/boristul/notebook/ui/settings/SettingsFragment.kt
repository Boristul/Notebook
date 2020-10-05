package com.boristul.notebook.ui.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.boristul.notebook.R
import com.boristul.uikit.ThemeSelector

class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        private const val THEME_PREF = "theme_type_pref"
        private const val ABOUT_PREF = "about_pref"
        private const val TEST_GD_PREF = "test_gd_pref"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkNotNull(findPreference<ListPreference>(THEME_PREF)).setOnPreferenceChangeListener { _, value ->
            ThemeSelector.setTheme((value as String).toInt()) // Cast because list prefs can't contain int arrays
            true
        }

        checkNotNull(findPreference(ABOUT_PREF)).setOnPreferenceClickListener {
            findNavController().navigate(R.id.action_settings_fragment_to_aboutFragment)
            true
        }

        checkNotNull(findPreference(TEST_GD_PREF)).setOnPreferenceClickListener {

            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("HelloWorld", "HelloWorld")
        super.onActivityResult(requestCode, resultCode, data)
    }
}
