package com.boristul.notebook.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.boristul.notebook.R
import com.boristul.notebook.databinding.ActivityMainBinding
import com.boristul.utils.findNavControllerInOnCreate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavControllerInOnCreate(binding.navHostFragment.id).apply {
            addOnDestinationChangedListener { _, destination, _ -> title = destination.label }
        }

        binding.navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_action_notes -> {
                    navController.navigate(R.id.nav_action_notes)
                    true
                }
                R.id.nav_action_settings -> {
                    navController.navigate(R.id.nav_action_settings)
                    true
                }
                else -> false
            }
        }
    }
}
