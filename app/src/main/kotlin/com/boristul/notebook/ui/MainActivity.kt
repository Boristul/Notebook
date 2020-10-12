package com.boristul.notebook.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.boristul.notebook.R
import com.boristul.notebook.databinding.ActivityMainBinding
import com.boristul.utils.findNavControllerInOnCreate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val appBarConfiguration by lazy { AppBarConfiguration(setOf(R.id.notes, R.id.settings)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavControllerInOnCreate(binding.navHostFragment.id)
        setupActionBarWithNavController(navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.navigation, navController)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, result: Intent?) {
        super.onActivityResult(requestCode, resultCode, result)

        if (resultCode == RESULT_OK && result != null) {
            viewModel.authorizedAccount.value =
                GoogleSignIn.getSignedInAccountFromIntent(result).getResult(ApiException::class.java)
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(binding.navHostFragment.id).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}
