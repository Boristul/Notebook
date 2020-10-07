package com.boristul.notebook.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.boristul.notebook.R
import com.boristul.notebook.ui.MainActivityViewModel
import com.boristul.uikit.ThemeSelector
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

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

        val activityViewModel by activityViewModels<MainActivityViewModel>()

        activityViewModel.authorizedAccount.observe(viewLifecycleOwner) { googleAccount ->
            if (googleAccount != null) {
                lifecycleScope.launch {
                    activityViewModel.testGoogleDrive(requireContext(), googleAccount)
                }
            }
        }

        checkNotNull(findPreference<ListPreference>(THEME_PREF)).setOnPreferenceChangeListener { _, value ->
            ThemeSelector.setTheme((value as String).toInt()) // Cast because list prefs can't contain int arrays
            true
        }

        checkNotNull(findPreference(ABOUT_PREF)).setOnPreferenceClickListener {
            findNavController().navigate(R.id.action_settings_fragment_to_aboutFragment)
            true
        }

        checkNotNull(findPreference(TEST_GD_PREF)).setOnPreferenceClickListener {
            activityViewModel.requestGoogleAccount(requireContext()).let { account ->
                if (account == null) {
                    startActivityForResult(
                        GoogleSignIn.getClient(requireContext(), activityViewModel.signInOptions).signInIntent,
                        1
                    )
                } else {
                    activityViewModel.authorizedAccount.value = account
                }
            }
            true
        }

        activityViewModel.resultMessageLiveData.observe(viewLifecycleOwner) { status ->
            if (status.isNotEmpty()) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.sf_get_files_title)
                    .setMessage(status)
                    .setPositiveButton(R.string.sf_ok_button, null)
                    .show()
            }
        }
    }
}
