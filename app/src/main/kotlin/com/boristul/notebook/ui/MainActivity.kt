package com.boristul.notebook.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.NavigationUI
import com.boristul.notebook.databinding.ActivityMainBinding
import com.boristul.utils.findNavControllerInOnCreate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavControllerInOnCreate(binding.navHostFragment.id).apply {
            addOnDestinationChangedListener { _, destination, _ -> title = destination.label }
        }
        NavigationUI.setupWithNavController(binding.navigation, navController)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, result: Intent?) {
        super.onActivityResult(requestCode, resultCode, result)

        if (resultCode == RESULT_OK && result != null) {
            viewModel.authorizedAccount.value =
                GoogleSignIn.getSignedInAccountFromIntent(result).getResult(ApiException::class.java)
        }
    }
}
